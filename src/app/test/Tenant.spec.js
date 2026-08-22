import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest'
import { mount, flushPromises } from '@vue/test-utils'
import axios from 'axios'
import Tenant from '../src/components/Tenant.vue'

const stubs = {
  HeaderBar: true,
  'va-inner-loading': { template: '<div><slot /></div>' },
  VaInnerLoading: { template: '<div><slot /></div>' },
  'va-form': { template: '<form><slot /></form>' },
  'va-input': { template: '<input />' },
  'va-button': { template: '<button @click="$emit(\'click\')"><slot /></button>' },
  'va-icon': true
}

const mountTenant = () => mount(Tenant, { global: { stubs } })

const setLocation = (search) => {
  Object.defineProperty(window, 'location', {
    value: { search, href: '' },
    writable: true,
    configurable: true
  })
}

describe('Tenant.vue', () => {
  beforeEach(() => {
    setLocation('')
    vi.spyOn(axios, 'get').mockResolvedValue({ data: {} })
    vi.spyOn(axios, 'post').mockResolvedValue({ data: {} })
  })

  afterEach(() => {
    vi.restoreAllMocks()
  })

  it('offers the ten dollar plan when the caller has no tenant', async () => {
    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('$10.00 / month')
    expect(wrapper.text()).toContain('Create tenant')
  })

  it('shows the active tenant when one is already provisioned', async () => {
    axios.get.mockResolvedValue({
      data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PROVISIONED' }
    })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('Acme')
    expect(wrapper.text()).toContain('acme.com')
    expect(wrapper.text()).toContain('tenant administrator')
  })

  it('prompts to finish payment when the request is still pending', async () => {
    axios.get.mockResolvedValue({
      data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PENDING_PAYMENT' }
    })
    axios.post.mockRejectedValue({ response: { data: { message: 'An active subscription is required' } } })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('waiting for payment')
    expect(wrapper.text()).toContain('Continue to payment')
  })

  it('claims the tenant when a paid checkout was never completed in the browser', async () => {
    axios.get.mockResolvedValue({
      data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PENDING_PAYMENT' }
    })
    axios.post.mockResolvedValue({
      data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PROVISIONED' }
    })

    const wrapper = mountTenant()
    await flushPromises()

    expect(axios.post).toHaveBeenCalledWith('api/subscribedtenant/req-1/tenant')
    expect(wrapper.text()).toContain('is active at acme.com')
  })

  it('reports a suspended tenant', async () => {
    axios.get.mockResolvedValue({
      data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'SUSPENDED' }
    })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('suspended')
    expect(wrapper.text()).toContain('Restart subscription')
  })

  it('provisions the tenant when returning from a successful checkout', async () => {
    setLocation('?request=req-1&status=success')
    axios.get.mockResolvedValue({ data: {} })
    axios.post.mockResolvedValue({
      data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PROVISIONED' }
    })

    const wrapper = mountTenant()
    await flushPromises()

    expect(axios.post).toHaveBeenCalledWith('api/subscribedtenant/req-1/tenant')
    expect(wrapper.text()).toContain('temporary administrator password')
  })

  it('does not charge again when returning from a cancelled checkout', async () => {
    setLocation('?request=req-1&status=cancelled')
    axios.get.mockResolvedValue({
      data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PENDING_PAYMENT' }
    })
    axios.post.mockRejectedValue({ response: { status: 400, data: { message: 'Unable to provision the tenant' } } })

    const wrapper = mountTenant()
    await flushPromises()

    expect(axios.post).not.toHaveBeenCalledWith('api/subscribedtenant/req-1/session')
    expect(wrapper.text()).toContain('Continue to payment')
  })

  it('surfaces the server message when provisioning is rejected', async () => {
    setLocation('?request=req-1&status=success')
    axios.post.mockRejectedValue({ response: { status: 400, data: { message: 'Unable to provision the tenant' } } })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('Unable to provision the tenant')
  })

  it('falls back to its own wording when the failure carries no message', async () => {
    setLocation('?request=req-1&status=success')
    axios.post.mockRejectedValue(new Error('Network Error'))

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('We could not confirm your subscription yet')
  })

  it('redirects to the payment provider when checkout starts', async () => {
    axios.get.mockResolvedValue({
      data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PENDING_PAYMENT' }
    })
    axios.post.mockImplementation((url) => {
      if (url.endsWith('/tenant')) {
        return Promise.reject({ response: { data: { message: 'An active subscription is required' } } })
      }
      return Promise.resolve({ data: { id: 'cs_test_1', url: 'https://checkout.stripe.com/c/pay/cs_test_1' } })
    })

    const wrapper = mountTenant()
    await flushPromises()

    await wrapper.find('button').trigger('click')
    await flushPromises()

    expect(axios.post).toHaveBeenCalledWith('api/subscribedtenant/req-1/session')
    expect(window.location.href).toBe('https://checkout.stripe.com/c/pay/cs_test_1')
  })
})

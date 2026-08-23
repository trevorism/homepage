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

const mockGets = (requestResponse, subscriptionResponse = { data: {} }) => {
  axios.get.mockImplementation((url) =>
    Promise.resolve(url.endsWith('/subscription') ? subscriptionResponse : requestResponse)
  )
}

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
    mockGets({
      data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PROVISIONED' }
    })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('Acme')
    expect(wrapper.text()).toContain('acme.com')
    expect(wrapper.text()).toContain('tenant administrator')
  })

  it('points the administrator at the tenant login rather than their own domain', async () => {
    mockGets({
      data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PROVISIONED' }
    })

    const wrapper = mountTenant()
    await flushPromises()

    const hrefs = wrapper.findAll('a').map((link) => link.attributes('href'))
    expect(hrefs).toContain('https://login.auth.trevorism.com')
    expect(hrefs).not.toContain('https://acme.com')
  })

  it('prompts to finish payment when the request is still pending', async () => {
    mockGets({
      data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PENDING_PAYMENT' }
    })
    axios.post.mockRejectedValue({ response: { data: { message: 'An active subscription is required' } } })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('waiting for payment')
    expect(wrapper.text()).toContain('Continue to payment')
  })

  it('claims the tenant when a paid checkout was never completed in the browser', async () => {
    mockGets({
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

  it('tells a recovered signup to look for the password email', async () => {
    mockGets({
      data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PENDING_PAYMENT' }
    })
    axios.post.mockResolvedValue({
      data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PROVISIONED' }
    })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('Check your email to set your administrator password')
  })

  it('shows the renewal date when the provider reports one', async () => {
    mockGets(
      { data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PROVISIONED' } },
      { data: { provider: 'STRIPE', state: 'ACTIVE', paidThrough: '2026-09-22T00:00:00Z' } }
    )

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('Your subscription is active and renews on')
    expect(wrapper.text()).toContain('2026')
  })

  it('still reports an active subscription when no renewal date is known', async () => {
    mockGets(
      { data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PROVISIONED' } },
      { data: { provider: 'STRIPE', state: 'ACTIVE' } }
    )

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('Your subscription is active.')
    expect(wrapper.text()).not.toContain('renews on')
  })

  it('claims nothing about the subscription when the provider cannot be read', async () => {
    mockGets(
      { data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PROVISIONED' } },
      { data: { state: 'UNKNOWN' } }
    )

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('is active at acme.com')
    expect(wrapper.text()).not.toContain('Your subscription is active')
    expect(wrapper.text()).not.toContain('could not find an active subscription')
  })

  it('offers to finish setup when a pending signup has already paid', async () => {
    mockGets(
      { data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PENDING_PAYMENT' } },
      { data: { provider: 'STRIPE', state: 'ACTIVE', paidThrough: '2026-09-22T00:00:00Z' } }
    )
    axios.post.mockRejectedValue({ response: { status: 400, data: { message: 'nope' } } })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('Your subscription is active and renews on')
    expect(wrapper.text()).toContain('Finish setting up my tenant')
    expect(wrapper.text()).not.toContain('Continue to payment')
  })

  it('asks a pending signup with no subscription to pay', async () => {
    mockGets(
      { data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PENDING_PAYMENT' } },
      { data: { provider: 'STRIPE', state: 'INACTIVE' } }
    )
    axios.post.mockRejectedValue({ response: { status: 400, data: { message: 'nope' } } })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('could not find an active subscription')
    expect(wrapper.text()).toContain('Continue to payment')
    expect(wrapper.text()).not.toContain('Finish setting up my tenant')
  })

  it('offers to restore a suspended tenant whose subscription is live again', async () => {
    mockGets(
      { data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'SUSPENDED' } },
      { data: { provider: 'STRIPE', state: 'ACTIVE' } }
    )
    axios.post.mockRejectedValue({ response: { status: 400, data: { message: 'nope' } } })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('Restore my tenant')
    expect(wrapper.text()).not.toContain('Restart subscription')
  })

  it('reports a suspended tenant while the subscription is still inactive', async () => {
    mockGets({
      data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'SUSPENDED' }
    })
    axios.post.mockRejectedValue({ response: { status: 400, data: { message: 'Unable to provision the tenant' } } })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('suspended')
    expect(wrapper.text()).toContain('Restart subscription')
  })

  it('restores a suspended tenant as soon as the subscription is live again', async () => {
    mockGets({
      data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'SUSPENDED' }
    })
    axios.post.mockResolvedValue({
      data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PROVISIONED' }
    })

    const wrapper = mountTenant()
    await flushPromises()

    expect(axios.post).toHaveBeenCalledWith('api/subscribedtenant/req-1/tenant')
    expect(wrapper.text()).toContain('is active at acme.com')
    expect(wrapper.text()).not.toContain('Restart subscription')
  })

  it('provisions the tenant when returning from a successful checkout', async () => {
    setLocation('?request=req-1&status=success')
    mockGets({ data: {} })
    axios.post.mockResolvedValue({
      data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PROVISIONED' }
    })

    const wrapper = mountTenant()
    await flushPromises()

    expect(axios.post).toHaveBeenCalledWith('api/subscribedtenant/req-1/tenant')
    expect(wrapper.text()).toContain('Check your email to set your administrator password')
  })

  it('does not charge again when returning from a cancelled checkout', async () => {
    setLocation('?request=req-1&status=cancelled')
    mockGets({
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
    axios.post.mockRejectedValue({
      response: { status: 400, data: { message: 'An active subscription is required before a tenant can be provisioned' } }
    })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('An active subscription is required before a tenant can be provisioned')
  })

  it('falls back to its own wording when the failure carries no message', async () => {
    setLocation('?request=req-1&status=success')
    axios.post.mockRejectedValue(new Error('Network Error'))

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('We could not confirm your subscription yet')
  })

  it('redirects to the payment provider when checkout starts', async () => {
    mockGets({
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

import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest'
import { mount, flushPromises } from '@vue/test-utils'
import axios from 'axios'
import Tenant from '../src/components/Tenant.vue'

const stubs = {
  HeaderBar: true,
  RouterLink: { template: '<a><slot /></a>' },
  'va-inner-loading': { template: '<div><slot /></div>' },
  VaInnerLoading: { template: '<div><slot /></div>' },
  'va-form': { template: '<form><slot /></form>' },
  'va-input': { template: '<input />' },
  'va-button': { template: '<button @click="$emit(\'click\')"><slot /></button>' },
  'va-alert': { template: '<div><slot /></div>' },
  'va-list': { template: '<div><slot /></div>' },
  'va-list-item': { template: '<div><slot /></div>' },
  'va-list-item-section': { template: '<div><slot /></div>' },
  'va-list-item-label': { template: '<div><slot /></div>' },
  'va-stepper': true,
  'va-icon': true
}

const mountTenant = (props = {}) =>
  mount(Tenant, { props: { pollDelays: [], ...props }, global: { stubs } })

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

const provisioned = {
  id: 'req-1',
  name: 'Acme',
  domain: 'acme.com',
  status: 'PROVISIONED',
  tenantGuid: 'guid-1',
  loginUrl: 'https://login.auth.trevorism.com/guid-1'
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

  it('starts a newcomer on the details step rather than a payment button', async () => {
    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.findAll('input')).toHaveLength(2)
    expect(wrapper.text()).toContain('not a website')
    expect(wrapper.text()).toContain('cannot be changed later')
    expect(wrapper.text()).not.toContain('Continue to Stripe')
  })

  it('reports a taken domain before the owner ever reaches payment', async () => {
    axios.post.mockResolvedValue({ data: { available: false, message: 'Domain acme.com is already in use' } })

    const wrapper = mountTenant()
    await flushPromises()

    const form = wrapper.findComponent({ name: 'TenantCreateForm' })
    form.vm.draftName = 'Acme'
    form.vm.draftDomain = 'acme.com'
    await form.vm.runCheck()
    await flushPromises()

    expect(axios.post).toHaveBeenCalledWith('api/subscribedtenant/availability', {
      name: 'Acme',
      domain: 'acme.com'
    })
    expect(wrapper.text()).toContain('Domain acme.com is already in use')
  })

  it('confirms a free domain before the owner reaches payment', async () => {
    axios.post.mockResolvedValue({ data: { available: true } })

    const wrapper = mountTenant()
    await flushPromises()

    const form = wrapper.findComponent({ name: 'TenantCreateForm' })
    form.vm.draftName = 'Acme'
    form.vm.draftDomain = 'acme.com'
    await form.vm.runCheck()
    await flushPromises()

    expect(wrapper.text()).toContain('are available')
  })

  it('lets an owner claim a tenant without naming a domain', async () => {
    axios.post.mockResolvedValue({ data: { available: true } })

    const wrapper = mountTenant()
    await flushPromises()

    const form = wrapper.findComponent({ name: 'TenantCreateForm' })
    form.vm.draftName = 'Acme'
    await form.vm.runCheck()
    await flushPromises()

    expect(axios.post).toHaveBeenCalledWith('api/subscribedtenant/availability', { name: 'Acme', domain: '' })
    expect(wrapper.text()).toContain('Acme is available')
    expect(form.vm.canContinue).toBe(true)
  })

  it('omits the domain from the review when none was given', async () => {
    const wrapper = mountTenant()
    await flushPromises()

    wrapper.vm.reviewDraft({ name: 'Acme', domain: '' })
    await flushPromises()

    expect(wrapper.text()).toContain('Review before paying')
    expect(wrapper.text()).not.toContain('Tenant domain')
  })

  it('explains the two accounts and the price before taking any money', async () => {
    const wrapper = mountTenant()
    await flushPromises()

    wrapper.vm.reviewDraft({ name: 'Acme', domain: 'acme.com' })
    await flushPromises()

    expect(wrapper.text()).toContain('Review before paying')
    expect(wrapper.text()).toContain('$10.00 / month, recurring until you cancel')
    expect(wrapper.text()).toContain('Your existing account')
    expect(wrapper.text()).toContain('separate account')
    expect(axios.post).not.toHaveBeenCalled()
  })

  it('only creates the request once the review step is confirmed', async () => {
    axios.post.mockImplementation((url) => {
      if (url === 'api/subscribedtenant') {
        return Promise.resolve({ data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PENDING_PAYMENT' } })
      }
      return Promise.resolve({ data: { url: 'https://checkout.stripe.com/c/pay/cs_1' } })
    })

    const wrapper = mountTenant()
    await flushPromises()
    wrapper.vm.reviewDraft({ name: 'Acme', domain: 'acme.com' })
    await flushPromises()

    await wrapper.find('button').trigger('click')
    await flushPromises()

    expect(axios.post).toHaveBeenCalledWith('api/subscribedtenant', { name: 'Acme', domain: 'acme.com' })
    expect(window.location.href).toBe('https://checkout.stripe.com/c/pay/cs_1')
  })

  it('never shows an error while the subscription webhook is still catching up', async () => {
    setLocation('?request=req-1&status=success')
    mockGets({ data: {} })
    axios.post.mockResolvedValue({ data: { id: 'req-1', name: 'Acme', status: 'PENDING_PAYMENT' } })

    const wrapper = mountTenant({ pollDelays: [0, 0] })
    await flushPromises()

    expect(wrapper.text()).toContain('Payment received')
    expect(wrapper.text()).not.toContain('could not confirm')
    expect(wrapper.text()).not.toContain('An active subscription is required')
  })

  it('reassures rather than alarms when provisioning outlasts the poll window', async () => {
    setLocation('?request=req-1&status=success')
    mockGets({ data: {} })
    axios.post.mockResolvedValue({ data: { id: 'req-1', name: 'Acme', status: 'PENDING_PAYMENT' } })

    const wrapper = mountTenant({ pollDelays: [0] })
    await flushPromises()

    expect(wrapper.text()).toContain('Your payment went through')
    expect(wrapper.text()).toContain('we will email you')
    expect(wrapper.text()).not.toContain('could not confirm')
  })

  it('reaches the ready state once a later poll succeeds', async () => {
    setLocation('?request=req-1&status=success')
    mockGets({ data: {} })
    let attempt = 0
    axios.post.mockImplementation(() => {
      attempt++
      return Promise.resolve({
        data: attempt < 3 ? { id: 'req-1', name: 'Acme', status: 'PENDING_PAYMENT' } : provisioned
      })
    })

    const wrapper = mountTenant({ pollDelays: [0, 0, 0] })
    await flushPromises()

    expect(wrapper.text()).toContain('Acme')
    expect(wrapper.text()).toContain('is active')
    expect(wrapper.text()).not.toContain('Setting up your tenant')
  })

  it('keeps polling through a failed attempt rather than giving up', async () => {
    setLocation('?request=req-1&status=success')
    mockGets({ data: {} })
    let attempt = 0
    axios.post.mockImplementation(() => {
      attempt++
      if (attempt === 1) {
        return Promise.reject(new Error('Network Error'))
      }
      return Promise.resolve({ data: provisioned })
    })

    const wrapper = mountTenant({ pollDelays: [0, 0] })
    await flushPromises()

    expect(wrapper.text()).toContain('is active')
  })

  it('hands the administrator their tenant id and login url', async () => {
    mockGets({ data: provisioned })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('guid-1')
    const hrefs = wrapper.findAll('a').map((link) => link.attributes('href'))
    expect(hrefs).toContain('https://login.auth.trevorism.com/guid-1')
    expect(hrefs).not.toContain('https://acme.com')
  })

  it('warns about the password deadline and offers a way to recover from it', async () => {
    mockGets({ data: provisioned })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('Set your password within 24 hours')
    const hrefs = wrapper.findAll('a').map((link) => link.attributes('href'))
    expect(hrefs).toContain('https://login.auth.trevorism.com/forgot/guid-1')
  })

  it('points a new tenant administrator at the admin console', async () => {
    mockGets({ data: provisioned })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('What to do next')
    const hrefs = wrapper.findAll('a').map((link) => link.attributes('href'))
    expect(hrefs).toContain('https://admin.auth.trevorism.com')
  })

  it('sends the owner to the billing portal instead of naming an unreachable provider', async () => {
    mockGets({ data: provisioned })
    axios.post.mockResolvedValue({ data: { url: 'https://billing.stripe.com/session/abc' } })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('Manage billing')
    const manageBilling = wrapper.findAll('button').find((button) => button.text().includes('Manage billing'))
    await manageBilling.trigger('click')
    await flushPromises()

    expect(axios.post).toHaveBeenCalledWith('api/subscribedtenant/portal')
    expect(window.location.href).toBe('https://billing.stripe.com/session/abc')
  })

  it('warns before access is lost when a payment has failed', async () => {
    const accessEndsOn = '2026-08-27T00:00:00Z'
    const expectedDeadline = new Date(accessEndsOn).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    })
    mockGets({ data: { ...provisioned, dateLapsed: '2026-08-20T00:00:00Z', accessEndsOn } })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain("could not collect this month's payment")
    expect(wrapper.text()).toContain(expectedDeadline)
    expect(wrapper.text()).toContain('Update payment method')
  })

  it('shows no payment warning on a healthy tenant', async () => {
    mockGets({ data: provisioned })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).not.toContain("could not collect this month's payment")
  })

  it('acknowledges a cancelled checkout instead of silently retrying', async () => {
    setLocation('?request=req-1&status=cancelled')
    mockGets({ data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PENDING_PAYMENT' } })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('Payment cancelled')
    expect(wrapper.text()).toContain('you have not been charged')
    expect(wrapper.text()).toContain('still reserved')
    expect(axios.post).not.toHaveBeenCalled()
  })

  it('does not blind fire a provisioning call for an unpaid pending request', async () => {
    mockGets(
      { data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PENDING_PAYMENT' } },
      { data: { provider: 'STRIPE', state: 'INACTIVE' } }
    )

    const wrapper = mountTenant()
    await flushPromises()

    expect(axios.post).not.toHaveBeenCalled()
    expect(wrapper.text()).toContain('Continue to payment')
  })

  it('finishes setup automatically when a pending request has already been paid', async () => {
    mockGets(
      { data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'PENDING_PAYMENT' } },
      { data: { provider: 'STRIPE', state: 'ACTIVE' } }
    )
    axios.post.mockResolvedValue({ data: provisioned })

    const wrapper = mountTenant()
    await flushPromises()

    expect(axios.post).toHaveBeenCalledWith('api/subscribedtenant/req-1/tenant')
    expect(wrapper.text()).toContain('is active')
  })

  it('shows the renewal date when the provider reports one', async () => {
    mockGets({ data: provisioned }, { data: { provider: 'STRIPE', state: 'ACTIVE', paidThrough: '2026-09-22T00:00:00Z' } })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('Your subscription is active and renews on')
    expect(wrapper.text()).toContain('2026')
  })

  it('claims nothing about the subscription when the provider cannot be read', async () => {
    mockGets({ data: provisioned }, { data: { state: 'UNKNOWN' } })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).not.toContain('Your subscription is active')
    expect(wrapper.text()).not.toContain('could not find an active subscription')
  })

  it('tells a suspended owner their data is retained', async () => {
    mockGets({ data: { ...provisioned, status: 'SUSPENDED' } }, { data: { provider: 'STRIPE', state: 'INACTIVE' } })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('suspended')
    expect(wrapper.text()).toContain('data are retained')
    expect(wrapper.text()).toContain('Restart subscription')
  })

  it('offers to restore a suspended tenant whose subscription is live again', async () => {
    mockGets({ data: { ...provisioned, status: 'SUSPENDED' } }, { data: { provider: 'STRIPE', state: 'ACTIVE' } })

    const wrapper = mountTenant()
    await flushPromises()

    expect(wrapper.text()).toContain('Restore my tenant')
    expect(wrapper.text()).not.toContain('Restart subscription')
  })

  it('surfaces the server message when setup is genuinely rejected', async () => {
    mockGets(
      { data: { id: 'req-1', name: 'Acme', domain: 'acme.com', status: 'SUSPENDED' } },
      { data: { provider: 'STRIPE', state: 'ACTIVE' } }
    )
    axios.post.mockRejectedValue({
      response: { status: 400, data: { message: 'This subscription already funds another tenant' } }
    })

    const wrapper = mountTenant()
    await flushPromises()

    const restore = wrapper.findAll('button').find((button) => button.text().includes('Restore my tenant'))
    await restore.trigger('click')
    await flushPromises()

    expect(wrapper.text()).toContain('This subscription already funds another tenant')
  })
})

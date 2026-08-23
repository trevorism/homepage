import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest'
import { mount, flushPromises } from '@vue/test-utils'
import axios from 'axios'
import Account from '../src/components/Account.vue'

const stubs = {
  HeaderBar: true,
  VaFileUpload: { template: '<div><slot /></div>' },
  'va-inner-loading': { template: '<div><slot /></div>' },
  'va-list': { template: '<div><slot /></div>' },
  'va-list-item': { template: '<div><slot /></div>' },
  'va-list-item-section': { template: '<div><slot /></div>' },
  'va-list-item-label': { template: '<div><slot /></div>' },
  'va-popover': { template: '<div><slot /></div>' },
  'va-icon': true,
  'va-chip': { name: 'VaChip', props: ['to'], template: '<a><slot /></a>' }
}

const mockGets = (user, tenant) => {
  axios.get.mockImplementation((url) => {
    if (url === 'api/user') {
      return Promise.resolve({ data: user })
    }
    if (url === 'api/tenant') {
      return Promise.resolve({ data: tenant })
    }
    return Promise.resolve({ data: [] })
  })
}

const mountAccount = async (user, tenant) => {
  mockGets(user, tenant)
  const wrapper = mount(Account, { global: { stubs } })
  await flushPromises()
  return wrapper
}

const changePasswordTarget = (wrapper) => wrapper.findComponent({ name: 'VaChip' }).props('to')

describe('Account.vue', () => {
  beforeEach(() => {
    vi.spyOn(axios, 'get').mockResolvedValue({ data: {} })
  })

  afterEach(() => {
    vi.restoreAllMocks()
  })

  it('names the tenant behind a tenant user', async () => {
    const wrapper = await mountAccount(
      { username: 'alice', email: 'alice@acme.com', tenantGuid: 'guid-1' },
      { name: 'Acme', domain: 'acme.com', guid: 'guid-1' }
    )

    expect(wrapper.text()).toContain('Acme')
    expect(wrapper.text()).toContain('acme.com')
  })

  it('sends a tenant user to the change form for their own tenant', async () => {
    const wrapper = await mountAccount(
      { username: 'alice', tenantGuid: 'guid-1' },
      { name: 'Acme', domain: 'acme.com', guid: 'guid-1' }
    )

    expect(changePasswordTarget(wrapper)).toEqual({ name: 'ChangePassword', params: { guid: 'guid-1' } })
  })

  it('says nothing about a tenant for a trevorism.com user', async () => {
    const wrapper = await mountAccount({ username: 'alice', email: 'alice@trevorism.com' }, {})

    expect(wrapper.text()).not.toContain('—')
    expect(changePasswordTarget(wrapper)).toEqual({ name: 'ChangePassword', params: { guid: undefined } })
  })

  it('still offers the change form when the tenant service cannot be read', async () => {
    axios.get.mockImplementation((url) => {
      if (url === 'api/user') {
        return Promise.resolve({ data: { username: 'alice', tenantGuid: 'guid-1' } })
      }
      return Promise.reject(new Error('unreachable'))
    })

    const wrapper = mount(Account, { global: { stubs } })
    await flushPromises()

    expect(changePasswordTarget(wrapper)).toEqual({ name: 'ChangePassword', params: { guid: 'guid-1' } })
  })
})

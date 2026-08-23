import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest'
import { mount, flushPromises } from '@vue/test-utils'
import axios from 'axios'
import ChangePassword from '../src/components/ChangePassword.vue'

const stubs = {
  HeaderBar: true,
  'va-form': { methods: { reset() {} }, template: '<form><slot /></form>' },
  'va-input': { template: '<input />' },
  'va-button': { template: '<button @click="$emit(\'click\')"><slot /></button>' },
  VaInnerLoading: { template: '<div><slot /></div>' },
  'va-alert': { template: '<div><slot /></div>' }
}

const push = vi.fn()

const mountChange = (guid) =>
  mount(ChangePassword, { props: { guid }, global: { stubs, mocks: { $router: { push } } } })

const fillAndSubmit = async (wrapper) => {
  wrapper.vm.username = 'alice'
  wrapper.vm.currentPassword = 'secret1'
  wrapper.vm.newPassword = 'secret2'
  wrapper.vm.repeatPassword = 'secret2'
  wrapper.vm.invokeButton()
  await flushPromises()
}

describe('ChangePassword.vue', () => {
  beforeEach(() => {
    push.mockClear()
    vi.spyOn(axios, 'post').mockResolvedValue({ data: true })
  })

  afterEach(() => {
    vi.restoreAllMocks()
  })

  it('changes the password inside the tenant named in the route', async () => {
    const wrapper = mountChange('20213392-c6bf-49c8-9cab-90b991b4a01f')
    await fillAndSubmit(wrapper)

    const [url, body] = axios.post.mock.calls[0]
    expect(url).toBe('/api/user/change')
    expect(body.tenantGuid).toBe('20213392-c6bf-49c8-9cab-90b991b4a01f')
    expect(body.username).toBe('alice')
    expect(push).toHaveBeenCalledWith('/tenant')
  })

  it('names no tenant when the route carries none', async () => {
    const wrapper = mountChange(undefined)
    await fillAndSubmit(wrapper)

    expect(axios.post.mock.calls[0][1].tenantGuid).toBeUndefined()
  })

  it('returns a trevorism.com password change to the profile page', async () => {
    const wrapper = mountChange(undefined)
    await fillAndSubmit(wrapper)

    expect(push).toHaveBeenCalledWith('/account')
  })

  it('reports a refusal from the tenant rather than routing away', async () => {
    axios.post.mockRejectedValue(new Error('rejected'))
    const wrapper = mountChange('guid-1')
    await fillAndSubmit(wrapper)

    expect(wrapper.text()).toContain('Unable to change password')
    expect(push).not.toHaveBeenCalled()
  })

  it('never posts when the new passwords disagree', async () => {
    const wrapper = mountChange('guid-1')
    wrapper.vm.username = 'alice'
    wrapper.vm.currentPassword = 'secret1'
    wrapper.vm.newPassword = 'secret2'
    wrapper.vm.repeatPassword = 'secret3'
    wrapper.vm.invokeButton()
    await flushPromises()

    expect(axios.post).not.toHaveBeenCalled()
    expect(wrapper.text()).toContain('do not match')
  })
})

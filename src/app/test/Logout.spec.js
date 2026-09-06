import { describe, it, expect, vi, beforeEach } from 'vitest'
import { mount, flushPromises } from '@vue/test-utils'
import Logout from '../src/components/Logout.vue'
import { logout } from '@trevorism/ui-auth'

vi.mock('@trevorism/ui-auth', () => ({
  logout: vi.fn(() => Promise.resolve())
}))

const stubs = { HeaderBar: true }

function mountLogout() {
  return mount(Logout, { global: { stubs } })
}

describe('Logout', () => {
  beforeEach(() => {
    logout.mockClear()
    logout.mockResolvedValue()
  })

  it('clears the session through the auth library', async () => {
    mountLogout()
    await flushPromises()

    expect(logout).toHaveBeenCalledTimes(1)
  })

  it('confirms the logout to the visitor', async () => {
    const wrapper = mountLogout()
    await flushPromises()

    expect(wrapper.text()).toContain('Bye!')
  })

  it('tells the visitor to clear cookies when the logout fails', async () => {
    logout.mockRejectedValue(new Error('nope'))

    const wrapper = mountLogout()
    await flushPromises()

    expect(wrapper.text()).toContain('clear cookies')
  })

  it('does not let a failing analytics reset block the logout', async () => {
    mountLogout()
    await flushPromises()

    expect(logout).toHaveBeenCalledTimes(1)
  })
})

import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import NotFound from '../src/components/NotFound.vue'

const stubs = { HeaderBar: true }

describe('NotFound.vue', () => {
  it('renders the 404 heading and message', () => {
    const wrapper = mount(NotFound, { global: { stubs } })
    const text = wrapper.text()
    expect(text).toContain('Not Found')
    expect(text).toContain('404 -- Page not found')
  })

  it('includes the shared header bar', () => {
    const wrapper = mount(NotFound, { global: { stubs } })
    expect(wrapper.findComponent({ name: 'HeaderBar' }).exists()).toBe(true)
  })
})

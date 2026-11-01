import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import About from '../src/components/About.vue'

const stubs = { HeaderBar: true, Stripe: true, 'va-button': true }

describe('About', () => {
  it('offers the donate form', () => {
    const wrapper = mount(About, { global: { stubs } })

    expect(wrapper.text()).toContain('Donate')
    expect(wrapper.findComponent({ name: 'Stripe' }).exists()).toBe(true)
  })
})

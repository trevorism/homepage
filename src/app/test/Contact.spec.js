import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import Contact from '../src/components/Contact.vue'

const stubs = {
  HeaderBar: true,
  Stripe: true,
  'va-chip': { props: ['to'], template: '<a :href="to"><slot /></a>' }
}

const mountContact = () => mount(Contact, { global: { stubs } })

describe('Contact.vue', () => {
  it('offers a tenant of your own alongside the funding section', () => {
    const text = mountContact().text()
    expect(text).toContain('Funding')
    expect(text).toContain('Your Own Tenant')
    expect(text).toContain('$10.00 / month')
  })

  it('links to the tenant setup page', () => {
    const wrapper = mountContact()
    const hrefs = wrapper.findAll('a').map((link) => link.attributes('href'))
    expect(hrefs).toContain('/tenant')
  })
})

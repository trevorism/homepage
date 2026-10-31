import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest'
import { mount, flushPromises, RouterLinkStub } from '@vue/test-utils'
import axios from 'axios'
import Assessment from '../src/components/Assessment.vue'

const field = {
  props: ['modelValue', 'label'],
  emits: ['update:modelValue'],
  template: '<input :data-label="label" :value="modelValue" @input="$emit(\'update:modelValue\', $event.target.value)" />'
}

const stubs = {
  HeaderBar: true,
  RouterLink: RouterLinkStub,
  'va-input': field,
  'va-textarea': field,
  'va-select': field,
  'va-button': { template: '<button class="submit" @click="$emit(\'click\')"><slot /></button>' },
  'va-alert': { template: '<div class="alert"><slot /></div>' }
}

const mountAssessment = () => mount(Assessment, { global: { stubs } })

const fill = async (wrapper, answers) => {
  for (const [label, value] of Object.entries(answers)) {
    await wrapper.find(`[data-label="${label}"]`).setValue(value)
  }
}

const completeAnswers = {
  'Your name': 'Pat',
  Email: 'pat@example.com',
  'Business name and what it does': "Pat's Bakery",
  "The one task you'd most like to stop doing by hand": 'Tracking wholesale orders',
  "Where you'd like it to run": 'hosted'
}

describe('Assessment.vue', () => {
  beforeEach(() => {
    vi.spyOn(axios, 'post').mockResolvedValue({ status: 202 })
  })

  afterEach(() => {
    vi.restoreAllMocks()
  })

  it('asks for every answer the plan needs', () => {
    const labels = mountAssessment().findAll('[data-label]').map((input) => input.attributes('data-label'))
    expect(labels).toEqual([
      'Email',
      'Your name',
      'Business name and what it does',
      'Team size',
      'Tools you pay for today',
      "The one task you'd most like to stop doing by hand",
      "Where you'd like it to run"
    ])
  })

  it('asks for a valid email before sending anything', async () => {
    const wrapper = mountAssessment()
    await fill(wrapper, { 'Your name': 'Pat', Email: 'not-an-email' })
    await wrapper.find('.submit').trigger('click')

    expect(axios.post).not.toHaveBeenCalled()
    expect(wrapper.find('.alert').text()).toContain('valid email')
  })

  it('sends a request with nothing but an email', async () => {
    const wrapper = mountAssessment()
    await fill(wrapper, { Email: 'pat@example.com' })
    await wrapper.find('.submit').trigger('click')
    await flushPromises()

    expect(axios.post.mock.calls[0][1]).toMatchObject({ email: 'pat@example.com', name: '', task: '' })
    expect(wrapper.text()).toContain("Thanks. We'll read this personally and reply within a week.")
  })

  it('sends the answers and thanks the visitor', async () => {
    const wrapper = mountAssessment()
    await fill(wrapper, completeAnswers)
    await wrapper.find('.submit').trigger('click')
    await flushPromises()

    const [url, body] = axios.post.mock.calls[0]
    expect(url).toBe('/api/assessment')
    expect(body).toMatchObject({ name: 'Pat', email: 'pat@example.com', task: 'Tracking wholesale orders', hosting: 'hosted', website: '' })
    expect(wrapper.text()).toContain("Thanks. We'll read this personally and reply within a week.")
    expect(wrapper.find('form').exists()).toBe(false)
  })

  it('explains when one network has sent too many requests', async () => {
    axios.post.mockRejectedValue({ response: { status: 429 } })
    const wrapper = mountAssessment()
    await fill(wrapper, completeAnswers)
    await wrapper.find('.submit').trigger('click')
    await flushPromises()

    expect(wrapper.find('.alert').text()).toContain('Too many requests')
  })

  it('offers the email address when anything else goes wrong', async () => {
    axios.post.mockRejectedValue(new Error('offline'))
    const wrapper = mountAssessment()
    await fill(wrapper, completeAnswers)
    await wrapper.find('.submit').trigger('click')
    await flushPromises()

    expect(wrapper.find('.alert').text()).toContain('feedback@trevorism.com')
  })

  it('keeps the bot trap out of reach of people and keyboards', () => {
    const trap = mountAssessment().find('.honeypot input')
    expect(trap.attributes('name')).toBe('website')
    expect(trap.attributes('tabindex')).toBe('-1')
    expect(trap.attributes('autocomplete')).toBe('off')
  })
})

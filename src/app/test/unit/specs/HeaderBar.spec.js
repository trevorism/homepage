import Vue from 'vue'
import HeaderBar from '@/components/HeaderBar'

describe('HeaderBar.vue', () => {
  it('should render correct contents', () => {
    const Constructor = Vue.extend(HeaderBar)
    const vm = new Constructor().$mount()
    expect(vm.$el.innerHTML)
      .to.contain('Trevorism')
  })
})

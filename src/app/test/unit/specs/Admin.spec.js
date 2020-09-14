import Vue from 'vue'
import Admin from '@/components/Admin'

describe('Admin.vue', () => {
  it('should render correct contents', () => {
    const Constructor = Vue.extend(Admin)
    const vm = new Constructor().$mount()
    expect(vm.$el.innerHTML)
      .to.contain('Only accessible to logged in users')
  })
})

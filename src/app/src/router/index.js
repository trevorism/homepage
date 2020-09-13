import Vue from 'vue'
import Router from 'vue-router'
import Splash from '@/components/Splash'
import Login from '@/components/Login'
import Admin from '@/components/Admin'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Splash',
      component: Splash
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/admin',
      name: 'Admin',
      component: Admin,
      beforeEnter: (to, from, next) => {
        let session = Vue.$cookies.get('session')
        if (session) {
          return next()
        }
        return next('/login')
      }
    }
  ]
})

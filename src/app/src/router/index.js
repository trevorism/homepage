import Vue from 'vue'
import Router from 'vue-router'
import Splash from '@/components/Splash'
import Login from '@/components/Login'
import Admin from '@/components/Admin'
import ForgotPassword from '@/components/ForgotPassword'

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
      path: '/logout',
      name: 'Logout',
      component: Splash
    },
    {
      path: '/forgot',
      name: 'Forgot',
      component: ForgotPassword
    },
    {
      path: '/admin',
      name: 'Admin',
      component: Admin,
      beforeEnter: (to, from, next) => {
        let admin = Vue.$cookies.get('admin')
        if (admin) {
          return next()
        }
        let returnUrl = window.location.origin + '/admin'
        return next('/login?return_url=' + returnUrl)
      }
    }
  ]
})

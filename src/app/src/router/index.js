import Vue from 'vue'
import Router from 'vue-router'
import Splash from '@/components/Splash'
import Login from '@/components/Login'
import Admin from '@/components/Admin'
import ForgotPassword from '@/components/ForgotPassword'
import Account from '@/components/Account'
import ChangePassword from '@/components/ChangePassword'
import Register from '@/components/Register'

Vue.use(Router)

function adminOnly (to, from, next, reRouteLocation) {
  let admin = Vue.$cookies.get('admin')
  if (admin) {
    return next()
  }
  return next(reRouteLocation)
}

function userOnly (to, from, next, reRouteLocation) {
  let username = Vue.$cookies.get('user_name')
  if (username) {
    return next()
  }
  if (reRouteLocation) {
    return next(reRouteLocation)
  }
  return next('/')
}

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
      path: '/account',
      name: 'Account',
      component: Account,
      beforeEnter: userOnly
    },
    {
      path: '/change',
      name: 'ChangePassword',
      component: ChangePassword,
      beforeEnter: userOnly
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/admin',
      name: 'Admin',
      component: Admin,
      beforeEnter: (to, from, next) => {
        let returnUrl = window.location.origin + '/admin'
        let reRoute = '/login?return_url=' + returnUrl
        return adminOnly(to, from, next, reRoute)
      }
    }
  ]
})

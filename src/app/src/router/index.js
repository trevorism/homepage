import { createRouter, createWebHistory } from 'vue-router'
import { useCookies } from "vue3-cookies";
import Splash from '../components/Splash.vue'
import Login from "../components/Login.vue";
import Logout from "../components/Logout.vue";
import ForgotPassword from "../components/ForgotPassword.vue";
import Account from "../components/Account.vue";
import ChangePassword from "../components/ChangePassword.vue";
import Register from "../components/Register.vue";
import Docs from "../components/Docs.vue";
import Apps from "../components/Apps.vue";
import Contact from "../components/Contact.vue";
import Prototype from "../components/articles/Prototype.vue";
import Trends from "../components/articles/Trends.vue";
import Production from "../components/articles/Production.vue";
import Improvement from "../components/articles/Improvement.vue";
import Admin from "../components/Admin.vue";

const { cookies } = useCookies();

function adminOnly (to, from, next, reRouteLocation) {
    let admin = cookies.get('admin')
    if (admin) {
        return next()
    }
    return next(reRouteLocation)
}

function userOnly (to, from, next, reRouteLocation) {
    let username = cookies.get('user_name')
    if (username) {
        return next()
    }
    if (reRouteLocation) {
        return next(reRouteLocation)
    }
    return next('/')
}

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
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
        component: Logout
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
        beforeEnter: (to, from, next) => {
            let returnUrl = window.location.origin + '/change'
            let reRoute = '/login?return_url=' + returnUrl
            return userOnly(to, from, next, reRoute)
        }
    },
    {
        path: '/register',
        name: 'Register',
        component: Register
    },
    {
        path: '/docs',
        name: 'Docs',
        component: Docs
    },
    {
        path: '/apps',
        name: 'Apps',
        component: Apps
    },
    {
        path: '/contact',
        name: 'Contact',
        component: Contact
    },
    {
        path: '/articles/prototype',
        name: 'prototype',
        component: Prototype
    },
    {
        path: '/articles/trends',
        name: 'trends',
        component: Trends
    },
    {
        path: '/articles/production',
        name: 'production',
        component: Production
    },
    {
        path: '/articles/improvement',
        name: 'improvement',
        component: Improvement
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

export default router

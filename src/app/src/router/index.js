import { createRouter, createWebHistory } from 'vue-router'
import { useCookies } from "vue3-cookies";
import Splash from '../components/Splash.vue'
import Logout from "../components/Logout.vue";
import Account from "../components/Account.vue";
import ChangePassword from "../components/ChangePassword.vue";
import Register from "../components/Register.vue";
import Apps from "../components/Apps.vue";
import Contact from "../components/Contact.vue";
import Prototype from "../components/articles/Prototype.vue";
import Trends from "../components/articles/Trends.vue";
import Production from "../components/articles/Production.vue";
import Improvement from "../components/articles/Improvement.vue";
import Admin from "../components/Admin.vue";
import NotFound from "../components/NotFound.vue";
import LayoutCaller from "../components/layout/layout-caller.vue";
import mixpanel from 'mixpanel-browser';

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
        path: '/logout',
        name: 'Logout',
        component: Logout
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
        component: ChangePassword
    },
    {
        path: '/register',
        name: 'Register',
        component: Register
    },
    {
        path: '/docs',
        name: 'docs',
        component: LayoutCaller
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
        path: "/:notFound",
        component: NotFound,
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

router.afterEach((to) => {
    mixpanel.track(to.fullPath)
})

export default router

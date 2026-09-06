import { createRouter, createWebHistory } from 'vue-router'
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
import Tenant from "../components/Tenant.vue";
import NotFound from "../components/NotFound.vue";
import LayoutCaller from "../components/layout/layout-caller.vue";

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
        meta: { requiresAuth: true }
    },
    {
        path: '/change/:guid?',
        name: 'ChangePassword',
        component: ChangePassword,
        props: true
    },
    {
        path: '/tenant',
        name: 'Tenant',
        component: Tenant,
        meta: { requiresAuth: true }
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
    }
]
})

export default router

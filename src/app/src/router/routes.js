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

export const pageRoutes = [
    {
        path: '/',
        name: 'Splash',
        component: Splash,
        meta: {
            title: 'Trevorism | Develop your software ecosystem',
            description: 'Trevorism is an open source platform of web services for authentication, data, actions and testing. Build software that balances complexity with elegance.'
        }
    },
    {
        path: '/docs',
        name: 'docs',
        component: LayoutCaller,
        meta: {
            title: 'Documentation | Trevorism',
            description: 'How the Trevorism platform works: its auth, data, action and testing services, the technology choices behind them, and links to each API and its source code.'
        }
    },
    {
        path: '/apps',
        name: 'Apps',
        component: Apps,
        meta: {
            title: 'Apps | Trevorism',
            description: 'Apps that run on the Trevorism platform, from goal tracking to changelogs and timelines, and how to run your own Trevorism tenant.'
        }
    },
    {
        path: '/contact',
        name: 'Contact',
        component: Contact,
        meta: {
            title: 'Contact | Trevorism',
            description: 'Contact Trevorism for pricing, feature requests or general questions, or set up your own Trevorism tenant.'
        }
    },
    {
        path: '/articles/prototype',
        name: 'prototype',
        component: Prototype,
        meta: {
            title: 'Prototype Driven Development | Trevorism',
            description: 'When a feature needs a complex change or new libraries, coding first, in a specific way, pays off. An introduction to prototype driven development.'
        }
    },
    {
        path: '/articles/trends',
        name: 'trends',
        component: Trends,
        meta: {
            title: 'Technology Trends | Trevorism',
            description: 'Computing power grows exponentially, software gets smaller and more distributed, and machines talk to machines. What that means for the software we build.'
        }
    },
    {
        path: '/articles/production',
        name: 'production',
        component: Production,
        meta: {
            title: 'Productionalized Web Service | Trevorism',
            description: 'What a complete, production-ready web service looks like: HTTPS, automated tests, continuous integration, zero-downtime deploys, monitoring and security.'
        }
    },
    {
        path: '/articles/improvement',
        name: 'improvement',
        component: Improvement,
        meta: {
            title: 'Scientific Improvement | Trevorism',
            description: 'A framework for sustained improvement, borrowed from the scientific method and software engineering, that works for individuals and scales to whole teams.'
        }
    }
]

export const appRoutes = [
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
    }
]

export const notFoundRoute = {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: NotFound,
    meta: { title: 'Not Found | Trevorism' }
}

export const routes = [...pageRoutes, ...appRoutes, notFoundRoute]

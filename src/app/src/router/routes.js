import Splash from '../components/Splash.vue'
import Logout from "../components/Logout.vue";
import Account from "../components/Account.vue";
import ChangePassword from "../components/ChangePassword.vue";
import Register from "../components/Register.vue";
import Apps from "../components/Apps.vue";
import Services from "../components/Services.vue";
import NoLockIn from "../components/NoLockIn.vue";
import AiBuiltIn from "../components/AiBuiltIn.vue";
import Assessment from "../components/Assessment.vue";
import About from "../components/About.vue";
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
            title: 'Elegant software for startups | Trevorism',
            description: 'Spend less time on spreadsheets and repetitive admin. We build the tools your business needs, and you own them. No vendor lock-in.'
        }
    },
    {
        path: '/services',
        name: 'Services',
        component: Services,
        meta: {
            title: 'Services and pricing | Trevorism',
            description: 'A free assessment, a fixed-price setup and a monthly plan to keep improving your tools, quoted to fit what your business needs. No lock-in.'
        }
    },
    {
        path: '/no-lock-in',
        name: 'NoLockIn',
        component: NoLockIn,
        meta: {
            title: 'No vendor lock-in | Trevorism',
            description: 'Your software, your data and open source code. Ask any time and we export your data for you.'
        }
    },
    {
        path: '/ai',
        name: 'AiBuiltIn',
        component: AiBuiltIn,
        meta: {
            title: 'AI built in | Trevorism',
            description: 'Let AI draft replies, summarize notes and sort requests, and connect your AI assistant to your business through MCP.'
        }
    },
    {
        path: '/assessment',
        name: 'Assessment',
        component: Assessment,
        meta: {
            title: 'Free software assessment for small businesses | Trevorism',
            description: 'Tell us about your business and its pain points.'
        }
    },
    {
        path: '/about',
        name: 'About',
        component: About,
        meta: {
            title: 'About Trevor | Trevorism',
            description: 'A software engineer who builds and runs the Trevorism platform and sets small businesses up on it.'
        }
    },
    {
        path: '/apps',
        name: 'Apps',
        component: Apps,
        meta: {
            title: 'Apps running on Trevorism | Trevorism',
            description: 'Real tools running on the Trevorism platform today, from goal tracking to changelogs and timelines. The same platform your business would run on.'
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

export const redirectRoutes = [
    {
        path: '/contact',
        redirect: '/assessment'
    }
]

export const notFoundRoute = {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: NotFound,
    meta: { title: 'Not Found | Trevorism' }
}

export const routes = [...pageRoutes, ...appRoutes, ...redirectRoutes, notFoundRoute]

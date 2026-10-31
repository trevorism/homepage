import { createRouter, createWebHistory } from 'vue-router'
import { routes } from './routes.js'
import { SITE_NAME } from '../seo.js'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

router.afterEach((to) => {
  document.title = to.meta.title ?? SITE_NAME
})

export default router

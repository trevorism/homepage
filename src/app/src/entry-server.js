import { createSSRApp } from 'vue'
import { createMemoryHistory, createRouter } from 'vue-router'
import { renderToString } from 'vue/server-renderer'
import VueClickAway from 'vue3-click-away'
import { createVuestic } from 'vuestic-ui'
import App from './App.vue'
import config from '../vuestic.config.js'
import { routes } from './router/routes.js'

export { pageRoutes, appRoutes, notFoundRoute } from './router/routes.js'
export { headTags, noindexHeadTags, pageFile, sitemap } from './seo.js'

export async function render(path) {
  const router = createRouter({ history: createMemoryHistory(), routes })
  const app = createSSRApp(App)
  app.use(router)
  app.use(VueClickAway)
  app.use(createVuestic({ config }))
  await router.push(path)
  await router.isReady()
  return renderToString(app)
}

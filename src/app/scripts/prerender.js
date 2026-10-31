import { mkdir, readFile, rm, writeFile } from 'node:fs/promises'
import { dirname, join } from 'node:path'
import { fileURLToPath, pathToFileURL } from 'node:url'

const APP_DIR = fileURLToPath(new URL('..', import.meta.url))
const DIST_DIR = join(APP_DIR, 'dist')
const PAGES_DIR = join(DIST_DIR, 'pages')
const TEMPLATE_PATH = join(DIST_DIR, 'index.html')
const TEMPLATE_TITLE = '<title>Trevorism</title>'
const APP_MOUNT = '<div id="app"></div>'

const { render, pageRoutes, appRoutes, redirectRoutes, notFoundRoute, headTags, noindexHeadTags, pageFile, sitemap } =
  await import(pathToFileURL(join(APP_DIR, 'dist-ssr', 'entry-server.js')).href)

const template = await readFile(TEMPLATE_PATH, 'utf8')
for (const marker of [TEMPLATE_TITLE, APP_MOUNT]) {
  if (!template.includes(marker)) {
    throw new Error(`${TEMPLATE_PATH} is missing ${marker}`)
  }
}

function documentWith(head, body = '') {
  return template
    .replace(TEMPLATE_TITLE, () => head)
    .replace(APP_MOUNT, () => `<div id="app">${body}</div>`)
}

async function writePage(file, content) {
  const target = join(PAGES_DIR, file)
  await mkdir(dirname(target), { recursive: true })
  await writeFile(target, content)
}

await rm(TEMPLATE_PATH)
for (const route of pageRoutes) {
  await writePage(pageFile(route.path), documentWith(headTags(route), await render(route.path)))
}
await writePage('_app.html', documentWith(noindexHeadTags()))
await writePage('_404.html', documentWith(noindexHeadTags(notFoundRoute.meta.title), await render('/_404')))
await writePage('_app-routes.json', JSON.stringify(appRoutes.map((route) => route.path)))
await writePage('_redirects.json', JSON.stringify(Object.fromEntries(redirectRoutes.map((route) => [route.path, route.redirect]))))
await writeFile(join(DIST_DIR, 'sitemap.xml'), sitemap(pageRoutes))

export const SITE_NAME = 'Trevorism'
export const SITE_ORIGIN = 'https://www.trevorism.com'
export const SHARE_IMAGE = { path: '/og-image.jpg', width: 1200, height: 630 }

const HTML_ESCAPES = { '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;' }

export function escapeHtml(text) {
  return String(text).replace(/[&<>"]/g, (character) => HTML_ESCAPES[character])
}

export function pageUrl(path) {
  return `${SITE_ORIGIN}${path}`
}

export function pageFile(path) {
  return path === '/' ? 'index.html' : `${path.slice(1)}.html`
}

export function headTags(route) {
  const title = escapeHtml(route.meta.title)
  const description = escapeHtml(route.meta.description)
  const url = pageUrl(route.path)
  return [
    `<title>${title}</title>`,
    `<meta name="description" content="${description}">`,
    `<link rel="canonical" href="${url}">`,
    `<meta property="og:site_name" content="${SITE_NAME}">`,
    `<meta property="og:type" content="website">`,
    `<meta property="og:title" content="${title}">`,
    `<meta property="og:description" content="${description}">`,
    `<meta property="og:url" content="${url}">`,
    `<meta property="og:image" content="${pageUrl(SHARE_IMAGE.path)}">`,
    `<meta property="og:image:width" content="${SHARE_IMAGE.width}">`,
    `<meta property="og:image:height" content="${SHARE_IMAGE.height}">`,
    `<meta name="twitter:card" content="summary_large_image">`
  ].join('\n    ')
}

export function noindexHeadTags(title = SITE_NAME) {
  return [
    `<title>${escapeHtml(title)}</title>`,
    `<meta name="robots" content="noindex">`
  ].join('\n    ')
}

export function sitemap(routes) {
  const urls = routes.map((route) => `  <url><loc>${pageUrl(route.path)}</loc></url>`)
  return [
    '<?xml version="1.0" encoding="UTF-8"?>',
    '<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">',
    ...urls,
    '</urlset>',
    ''
  ].join('\n')
}

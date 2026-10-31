import { describe, it, expect } from 'vitest'
import { escapeHtml, headTags, noindexHeadTags, pageFile, sitemap } from '../src/seo.js'

const docs = { path: '/docs', meta: { title: 'Docs & "APIs"', description: 'Read <this>' } }

describe('seo', () => {
  it('escapes text placed into html', () => {
    expect(escapeHtml('<a href="x">Tom & Jerry</a>')).toBe('&lt;a href=&quot;x&quot;&gt;Tom &amp; Jerry&lt;/a&gt;')
  })

  it('describes a page with its title, description, canonical url and share card', () => {
    const head = headTags(docs)
    expect(head).toContain('<title>Docs &amp; &quot;APIs&quot;</title>')
    expect(head).toContain('<meta name="description" content="Read &lt;this&gt;">')
    expect(head).toContain('<link rel="canonical" href="https://www.trevorism.com/docs">')
    expect(head).toContain('<meta property="og:image" content="https://www.trevorism.com/og-image.jpg">')
    expect(head).toContain('<meta name="twitter:card" content="summary_large_image">')
    expect(head).not.toContain('noindex')
  })

  it('keeps app pages out of the index', () => {
    expect(noindexHeadTags()).toContain('<meta name="robots" content="noindex">')
    expect(noindexHeadTags('Not Found | Trevorism')).toContain('<title>Not Found | Trevorism</title>')
  })

  it('names each page file after its path', () => {
    expect(pageFile('/')).toBe('index.html')
    expect(pageFile('/docs')).toBe('docs.html')
    expect(pageFile('/articles/production')).toBe('articles/production.html')
  })

  it('lists every page in the sitemap', () => {
    const xml = sitemap([{ path: '/' }, docs])
    expect(xml).toContain('<loc>https://www.trevorism.com/</loc>')
    expect(xml).toContain('<loc>https://www.trevorism.com/docs</loc>')
  })
})

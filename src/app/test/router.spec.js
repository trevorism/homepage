import { describe, it, expect } from 'vitest'
import router from '../src/router/index.js'
import { pageRoutes } from '../src/router/routes.js'

const SEARCH_SNIPPET_LENGTH = 160

describe('router', () => {
  it('resolves the change form for a tenant', () => {
    const route = router.resolve('/change/20213392-c6bf-49c8-9cab-90b991b4a01f')

    expect(route.name).toBe('ChangePassword')
    expect(route.params.guid).toBe('20213392-c6bf-49c8-9cab-90b991b4a01f')
  })

  it('still resolves the change form without a tenant', () => {
    const route = router.resolve('/change')

    expect(route.name).toBe('ChangePassword')
    expect(route.params.guid).toBeUndefined()
  })

  it('builds the tenant change path from a named target', () => {
    const route = router.resolve({ name: 'ChangePassword', params: { guid: 'guid-1' } })

    expect(route.path).toBe('/change/guid-1')
  })

  it('builds the plain change path when there is no tenant', () => {
    const route = router.resolve({ name: 'ChangePassword', params: { guid: undefined } })

    expect(route.path).toBe('/change')
  })

  it('marks the pages that need a session so the auth guard picks them up', () => {
    for (const path of ['/account', '/tenant']) {
      expect(router.resolve(path).meta.requiresAuth, path).toBe(true)
    }
  })

  it('leaves the public pages open', () => {
    for (const path of ['/', '/register', '/contact', '/apps', '/logout']) {
      expect(router.resolve(path).meta.requiresAuth, path).toBeUndefined()
    }
  })

  it('gives every indexed page a title and a description that fits a search snippet', () => {
    for (const route of pageRoutes) {
      expect(route.meta.title, route.path).toBeTruthy()
      expect(route.meta.description, route.path).toBeTruthy()
      expect(route.meta.description.length, route.path).toBeLessThanOrEqual(SEARCH_SNIPPET_LENGTH)
    }
  })

  it('shows the not found page for unknown nested paths', () => {
    expect(router.resolve('/articles/missing').name).toBe('NotFound')
  })

  it('titles the browser tab after the page it navigates to', async () => {
    await router.push('/docs')
    expect(document.title).toBe('Documentation | Trevorism')
  })
})

import { describe, it, expect } from 'vitest'
import router from '../src/router/index.js'

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
})

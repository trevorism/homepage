<template>
  <footer class="site-footer mt-12 border-t">
    <div class="container mx-auto flex flex-wrap items-center justify-between gap-4 px-6 py-6 text-sm">
      <nav class="flex flex-wrap gap-x-6 gap-y-2">
        <router-link v-for="link in links" :key="link.to" :to="link.to" class="underline">{{ link.text }}</router-link>
      </nav>
      <p class="text-slate-500">© Trevorism LLC</p>
    </div>
  </footer>
</template>

<script setup>
const links = [
  { to: '/services', text: 'Services' },
  { to: '/no-lock-in', text: 'No lock-in' },
  { to: '/ai', text: 'AI built in' },
  { to: '/assessment', text: 'Free assessment' },
  { to: '/about', text: 'About' },
  { to: '/apps', text: 'Apps' },
  { to: '/docs', text: 'Docs' },
  { to: '/privacy', text: 'Privacy' }
]
</script>

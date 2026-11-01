<template>
  <div id="no-lock-in">
    <HeaderBar :local="true"></HeaderBar>
    <div class="container mx-auto max-w-3xl px-6 py-10">
      <p class="text-sm font-bold text-slate-500 mb-2">No vendor lock-in</p>
      <h1 class="text-3xl font-bold mb-8">Software you can walk away from is software you can trust.</h1>
      <ol class="mb-10">
        <li v-for="(promise, index) in promises" :key="promise.title" class="mb-6">
          <h2 class="text-xl font-bold mb-1">{{ index + 1 }}. {{ promise.title }}</h2>
          <p>{{ promise.detail }}</p>
        </li>
      </ol>
      <va-button color="success" to="/assessment">Get a free assessment</va-button>
    </div>
  </div>
</template>

<script setup>
import HeaderBar from '@trevorism/ui-header-bar'

const promises = [
  {
    title: 'The code is open.',
    detail: "Every part of the platform is public on GitHub. Nothing is hidden."
  },
  {
    title: 'Your data is yours.',
    detail: 'Everything your business stores belongs to you. Ask, and we export all of it.'
  },
  {
    title: "You're in control.",
    detail: 'Prefer your own cloud account? We can set your software up there, where you decide who has access.'
  }
]
</script>

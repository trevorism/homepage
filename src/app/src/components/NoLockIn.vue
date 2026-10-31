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
    detail: "Every part of the platform is public on GitHub. Nothing is hidden, and it doesn't need Trevorism infrastructure to run."
  },
  {
    title: 'Your data is yours, wherever it runs.',
    detail: "All data for your business is owned by you. In your own Google Cloud account it's yours from day one; if we host it, we move it into your own account whenever you ask."
  },
  {
    title: 'You choose where it runs, and can change your mind.',
    detail: 'In your own account, we work with the access you grant and you can remove it at any time; your software keeps running. Hosted by us, you can move to your own account whenever you like.'
  }
]
</script>

<template>
  <div id="about">
    <HeaderBar :local="true"></HeaderBar>
    <div class="container mx-auto max-w-3xl px-6 py-10">
      <h1 class="text-3xl font-bold mb-8">About</h1>
      <div class="flex flex-col items-start gap-6 sm:flex-row">
        <div class="photo-placeholder flex h-32 w-32 shrink-0 items-center justify-center rounded-full bg-slate-200 text-4xl font-bold text-slate-600">
          T
        </div>
        <div>
          <h2 class="text-xl font-bold mb-2">Trevor</h2>
          <p class="mb-4">
            Trevor is a software engineer and the founder of Trevorism LLC. He built and runs the Trevorism platform, a
            reference implementation of an enterprise software ecosystem. With 20 years experience, he knows how to impact
            businesses by reducing friction through automation.
          </p>
          <va-button color="success" to="/assessment">Get a free assessment</va-button>
        </div>
      </div>
      <h2 class="text-xl font-bold mt-10 mb-2">Donate</h2>
      <p class="mb-2">Help fund Trevorism by making a payment below. We certainly would appreciate it!</p>
      <div class="mt-6"><Stripe></Stripe></div>
    </div>
  </div>
</template>

<script setup>
import HeaderBar from '@trevorism/ui-header-bar'
import Stripe from './Stripe.vue'
</script>

<template>
  <div id="splash">
    <HeaderBar :local="true"></HeaderBar>

    <section class="glassBackground">
      <div class="container mx-auto px-6 py-16">
        <div class="max-w-2xl rounded-md bg-white/90 p-8 shadow">
          <h1 class="text-4xl font-bold mb-4">Own Your Business Functions</h1>
          <p class="text-lg mb-6">
            Stop losing hours to manual work and tools that don't talk to each other. We build software
            around how your business works, and it's yours: your data, your code, no lock-in.
          </p>
          <div class="flex flex-wrap items-center gap-6">
            <va-button color="success" to="/assessment">Get a free assessment</va-button>
            <a href="#how-it-works" class="underline">See how it works ↓</a>
          </div>
        </div>
      </div>
    </section>

    <section id="how-it-works" class="container mx-auto px-6 py-12">
      <h2 class="text-2xl font-bold mb-6">How it works</h2>
      <ol class="grid gap-6 md:grid-cols-3">
        <li v-for="(step, index) in steps" :key="step.title" class="rounded-md border p-6">
          <p class="text-sm font-bold text-slate-500 mb-2">Step {{ index + 1 }}</p>
          <h3 class="text-lg font-bold mb-2">{{ step.title }}</h3>
          <p>{{ step.detail }}</p>
        </li>
      </ol>
    </section>

    <section class="bg-slate-100">
      <div class="container mx-auto px-6 py-12">
        <h2 class="text-2xl font-bold mb-6">What changes for you</h2>
        <ul class="grid gap-6 md:grid-cols-2">
          <li v-for="outcome in outcomes" :key="outcome.title">
            <h3 class="text-lg font-bold mb-1">{{ outcome.title }}</h3>
            <p>{{ outcome.detail }}</p>
          </li>
        </ul>
      </div>
    </section>

    <section class="container mx-auto px-6 py-12">
      <h2 class="text-2xl font-bold mb-6">AI that does the busywork</h2>
      <ul class="grid gap-6 md:grid-cols-2 mb-4">
        <li v-for="use in aiUses" :key="use.title">
          <h3 class="text-lg font-bold mb-1">{{ use.title }}</h3>
          <p>{{ use.detail }}</p>
        </li>
      </ul>
      <router-link to="/ai" class="underline">How AI fits in</router-link>
    </section>

    <section class="bg-slate-100">
      <div class="container mx-auto px-6 py-12">
        <h2 class="text-2xl font-bold mb-4">Already running on Trevorism</h2>
        <p class="mb-2">Several apps have been built using the platform capabilities. The Trevorism platform enables fast time to market for business applications.</p>
        <router-link to="/apps" class="underline">See the apps</router-link>
      </div>
    </section>

    <section>
      <div class="container mx-auto px-6 py-12 text-center">
        <h2 class="text-2xl font-bold mb-4">Not sure what you need?</h2>
        <p class="mb-6">
          That's what the assessment is for. Tell us what slows you down and we'll tell you what we'd build, in writing,
          for free.
        </p>
        <va-button color="success" to="/assessment">Get a free assessment</va-button>
      </div>
    </section>
  </div>
</template>

<script setup>
import HeaderBar from '@trevorism/ui-header-bar';

const steps = [
  {
    title: 'Determine business capabilities in need of automation',
    detail: 'Tell us where the time goes: the spreadsheets, the copy and paste, the tasks you repeat every week. You get a written plan back, free.'
  },
  {
    title: 'Customize Trevorism for you',
    detail: 'Using platform tooling, we build a custom solution from already built enterprise functionality, hosted by us or, if you prefer, set up in your own cloud account.'
  },
  {
    title: 'You own it',
    detail: "The software and data are yours. Take control of what's been built and use us as support when needed."
  }
]

const outcomes = [
  {
    title: 'Hours back every week',
    detail: 'The work you repeat by hand runs itself.'
  },
  {
    title: 'Fewer subscriptions',
    detail: 'One system replaces the patchwork of tools you pay for.'
  },
  {
    title: 'Your data, always',
    detail: 'The software and everything in it belong to you. Ask any time and we export your data for you.'
  },
  {
    title: 'Room to grow',
    detail: 'Start with one task and add more as your business changes, without switching tools.'
  },
  {
    title: 'Reliable from day one',
    detail: 'The platform behind it already runs about 40 production services and is tested on every change.'
  }
]

const aiUses = [
  {
    title: 'Answer and sort faster',
    detail: 'Draft replies, summarize notes and sort incoming requests automatically.'
  },
  {
    title: 'Ask your business anything',
    detail: 'Connect your AI assistant and ask it to look up records, add new ones or run tasks, with only your permissions.'
  }
]
</script>

<style>
.va-dropdown__content{
  z-index: 1500;
}
</style>

<style scoped>
  .glassBackground {
    background-color: #D9E7F2;
    background-image: url("../assets/glassbuildingmed.jpg");
    background-repeat: no-repeat;
    background-position: right;
    background-size: cover;
  }
</style>

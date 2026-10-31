<template>
  <div id="splash">
    <HeaderBar :local="true"></HeaderBar>

    <section class="glassBackground">
      <div class="container mx-auto px-6 py-16">
        <div class="max-w-2xl rounded-md bg-white/90 p-8 shadow">
          <h1 class="text-4xl font-bold mb-4">Own your business functions.</h1>
          <p class="text-lg mb-6">
            The Trevorism platform allows for simple and progressive inclusion of common business functions. We can
            host it for you or run it in your own Google Cloud account. Either way, the software and the data are yours.
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
        <h2 class="text-2xl font-bold mb-6">Why it's different</h2>
        <ul class="grid gap-6 md:grid-cols-2">
          <li v-for="reason in reasons" :key="reason.title">
            <h3 class="text-lg font-bold mb-1">{{ reason.title }}</h3>
            <p>{{ reason.detail }}</p>
          </li>
        </ul>
      </div>
    </section>

    <section class="container mx-auto px-6 py-12">
      <h2 class="text-2xl font-bold mb-6">AI built in</h2>
      <ul class="grid gap-6 md:grid-cols-2 mb-4">
        <li v-for="capability in aiCapabilities" :key="capability.title">
          <h3 class="text-lg font-bold mb-1">{{ capability.title }}</h3>
          <p>{{ capability.detail }}</p>
        </li>
      </ul>
      <router-link to="/ai" class="underline">How AI fits in</router-link>
    </section>

    <section class="bg-slate-100">
      <div class="container mx-auto px-6 py-12">
        <h2 class="text-2xl font-bold mb-4">Already running on Trevorism</h2>
        <p class="mb-2">Goals, prompt, timeline, changelog, health-dash and billing all run on the platform today.</p>
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
    title: 'Evaluate your business needs',
    detail: 'Which tools you pay for, what lives in spreadsheets, what you do by hand every week. You get a written plan back, free.'
  },
  {
    title: 'Customize Trevorism for you',
    detail: 'Using platform tooling, we create a custom solution integrating already built enterprise functionality, hosted by us or in your own Google Cloud account.'
  },
  {
    title: 'You own it',
    detail: 'The software and data are yours wherever they run. Take control of what has been built and use us as support when needed.'
  }
]

const aiCapabilities = [
  {
    title: 'Your tools can use AI',
    detail: 'Draft replies, summarize notes and sort incoming requests with models from Anthropic or OpenAI, through one interface, so you can change models without rebuilding.'
  },
  {
    title: 'Your AI assistant can work with your business',
    detail: 'Through MCP, assistants such as Claude can look up your records, add new ones and run tasks, using your login and only your permissions.'
  }
]

const reasons = [
  {
    title: 'You own it, wherever it runs',
    detail: 'The platform is open source and your data is yours, whether we host it or it runs in your own Google Cloud account.'
  },
  {
    title: 'Your choice of hosting',
    detail: 'Let us run it for you, or keep it in your own Google Cloud account and pay Google directly. You can switch later.'
  },
  {
    title: 'One place, not ten subscriptions',
    detail: 'Logins, data and automation live together and grow together.'
  },
  {
    title: 'Built to last',
    detail: 'About 40 production services run on this platform, checked by 110+ automated test suites on every change.'
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

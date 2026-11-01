<template>
  <div id="services">
    <HeaderBar :local="true"></HeaderBar>
    <div class="container mx-auto px-6 py-10">
      <h1 class="text-3xl font-bold mb-6">Services and pricing</h1>

      <div class="overflow-x-auto mb-10">
        <table class="w-full border-collapse text-left">
          <thead>
            <tr>
              <th class="border-b p-3"></th>
              <th v-for="plan in plans" :key="plan.name" class="border-b p-3 text-lg">{{ plan.name }}</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="row in planRows" :key="row.label">
              <th class="border-b p-3 align-top">{{ row.label }}</th>
              <td v-for="plan in plans" :key="plan.name" class="border-b p-3 align-top">{{ plan[row.field] }}</td>
            </tr>
          </tbody>
        </table>
      </div>

      <h2 class="text-2xl font-bold mb-4">Where it runs</h2>
      <div class="overflow-x-auto mb-4">
        <table class="w-full border-collapse text-left">
          <thead>
            <tr>
              <th class="border-b p-3"></th>
              <th class="border-b p-3 text-lg">Hosted by us</th>
              <th class="border-b p-3 text-lg">In your own account</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="row in hostingRows" :key="row.label">
              <th class="border-b p-3 align-top">{{ row.label }}</th>
              <td class="border-b p-3 align-top">{{ row.hostedByUs }}</td>
              <td class="border-b p-3 align-top">{{ row.ownAccount }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <p class="mb-10">Not sure? Start hosted by us and move whenever you like.</p>

      <h2 class="text-2xl font-bold mb-4">Questions</h2>
      <dl class="mb-10">
        <div v-for="item in faq" :key="item.question" class="mb-4">
          <dt class="font-bold">{{ item.question }}</dt>
          <dd>
            {{ item.answer }}
            <template v-if="item.link">
              See <router-link :to="item.link.to" class="underline">{{ item.link.text }}</router-link>.
            </template>
          </dd>
        </div>
      </dl>

      <va-button color="success" to="/assessment">Get a free assessment</va-button>
    </div>
  </div>
</template>

<script setup>
import HeaderBar from '@trevorism/ui-header-bar'

const plans = [
  {
    name: 'Free assessment',
    price: 'Free',
    time: 'A few days',
    includes: 'A written plan for what to automate first'
  },
  {
    name: 'Starter',
    price: 'Fixed quote after the assessment',
    time: 'A month',
    includes: 'Your first tasks automated, built around how you work'
  },
  {
    name: 'Grow',
    price: 'Monthly, based on what you need',
    time: 'Ongoing',
    includes: 'New tools and improvements as your business changes, plus support'
  }
]

const planRows = [
  { label: 'Price', field: 'price' },
  { label: 'Time', field: 'time' },
  { label: 'You get', field: 'includes' }
]

const hostingRows = [
  { label: 'Cloud account', hostedByUs: 'Ours', ownAccount: "Yours, in your company's name" },
  { label: 'Hosting bill', hostedByUs: 'Included in your monthly plan', ownAccount: 'Paid by you, directly to your cloud provider' },
  { label: 'Your data', hostedByUs: 'Yours. We move it to your own account whenever you ask', ownAccount: 'Yours, in your account from day one' },
  { label: 'Best for', hostedByUs: 'Getting started with nothing to manage', ownAccount: 'Businesses that want full control' }
]

const faq = [
  {
    question: 'How much does it cost?',
    answer: 'It depends on what you need. After the free assessment we quote a fixed price for setup and a monthly plan that fits.'
  },
  {
    question: 'Do I need a developer?',
    answer: 'No. We build it and show your team how to use it.'
  },
  {
    question: 'Can it use AI?',
    answer: 'Yes. It can draft replies, summarize notes and sort requests, and you can connect your AI assistant.',
    link: { to: '/ai', text: 'AI built in' }
  },
  {
    question: 'What if I want to leave?',
    answer: 'Your software and data come with you.',
    link: { to: '/no-lock-in', text: 'No lock-in' }
  }
]
</script>

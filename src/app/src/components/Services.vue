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
      <p class="mb-10">Not sure? Start hosted by us and move to your own account whenever you like.</p>

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
    includes: 'A written plan for your business'
  },
  {
    name: 'Starter',
    price: 'Fixed quote after the assessment',
    time: 'A month',
    includes: 'A custom solution for your business leveraging the platform'
  },
  {
    name: 'Grow',
    price: "Monthly, based on what you run and where it's hosted",
    time: 'Ongoing',
    includes: 'New tools and improvements, support, and hosting if we host it'
  }
]

const planRows = [
  { label: 'Price', field: 'price' },
  { label: 'Time', field: 'time' },
  { label: 'You get', field: 'includes' }
]

const hostingRows = [
  { label: 'Google Cloud account', hostedByUs: 'Ours', ownAccount: "Yours, in your company's name" },
  { label: 'Hosting bill', hostedByUs: 'Included in your monthly plan', ownAccount: 'Paid by you, directly to Google' },
  { label: 'Your data', hostedByUs: 'Yours. We move it to your own account whenever you ask', ownAccount: 'Yours, in your account from day one' },
  { label: 'Best for', hostedByUs: 'Getting started with nothing to manage', ownAccount: 'Businesses that want full control' }
]

const faq = [
  {
    question: 'How much does it cost?',
    answer: 'It depends on what you need. After the free assessment we quote a fixed price for setup and a monthly plan that fits what you run.'
  },
  {
    question: 'Do I need a developer?',
    answer: 'No. We build it and show your team how to use it.'
  },
  {
    question: 'Do I need a Google Cloud account?',
    answer: "Only if you want it in your own account. We'll help you create one in your company's name. Otherwise we host it for you."
  },
  {
    question: 'Can I switch later?',
    answer: 'Yes. Start hosted by us and move to your own account whenever you like; we move the software and data for you.'
  },
  {
    question: 'What access do you have?',
    answer: 'In your own account, only the access you grant, and you can remove it at any time.'
  },
  {
    question: 'What if I want to leave?',
    answer: 'Your software and data come with you. In your own account, remove our access and everything keeps running. If we host it, we move it into your own Google Cloud account first.',
    link: { to: '/no-lock-in', text: 'No lock-in' }
  },
  {
    question: 'Can it use AI?',
    answer: 'Yes. Your tools can use models from Anthropic or OpenAI, and assistants such as Claude can work with your business data through MCP.',
    link: { to: '/ai', text: 'AI built in' }
  },
  {
    question: 'What does it run on?',
    answer: 'Google Cloud, with every change tested before it ships.'
  }
]
</script>

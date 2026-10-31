<template>
  <div id="apps">
    <HeaderBar :local="true"></HeaderBar>
    <h1 class="text-3xl font-bold ml-4 mb-2">Apps</h1>
    <p class="ml-4 mb-2">Everything here runs on the platform your business would get.</p>
    <ul class="list-disc ml-10">
      <li>
        <va-chip class="w-1/6" flat href="https://active.project.trevorism.com">Service Registry</va-chip> View a list
        of all Trevorism integrated services and libraries
      </li>
      <li class="mt-2">
        <va-chip class="w-1/6" flat href="https://health-dash.testing.trevorism.com">Health Dashboard</va-chip> View
        health and monitoring status of Trevorism services
      </li>
      <li class="mt-2">
        <va-chip class="w-1/6" flat href="https://changelog.project.trevorism.com">Changelog</va-chip> Shows key changes
        to the platform over time.
      </li>
      <li class="mt-2">
        <va-chip class="w-1/6" flat href="https://timeline.draw.trevorism.com">Timeline Generator</va-chip> Create a
        downloadable color coded timeline with pixel perfect scale
      </li>
      <li class="mt-2">
        <va-chip class="w-1/6" flat href="https://prompt.action.trevorism.com">Ask a Question</va-chip> Ask questions of
        other Trevorism users and get answers
      </li>
      <li class="mt-2">
        <va-chip class="w-1/6" flat href="https://goals.action.trevorism.com">Goals</va-chip> Track long-term goals
        with metrics and progress
      </li>
      <li class="mt-2">
        <va-chip class="w-1/6" flat href="https://candlebot.trade.trevorism.com">Crypto Charts</va-chip> View
        cryptocurrency financial charts
      </li>
      <li class="mt-2">
        <va-chip class="w-1/6" flat href="https://billing.trade.trevorism.com">Crypto Payments</va-chip> Send or receive
        crypto payments
      </li>
      <li class="mt-2">
        <va-chip class="w-1/6" flat href="https://memowand.com">Memowand</va-chip> Private group image sharing
      </li>
    </ul>

    <p class="ml-4 mt-6 mb-2">
      Want something like this for your business?
      <router-link to="/assessment" class="underline">Get a free assessment</router-link>.
    </p>
  </div>
</template>

<script>
import HeaderBar from '@trevorism/ui-header-bar'

export default {
  name: 'Apps',
  components: { HeaderBar }
}
</script>

<style scoped></style>

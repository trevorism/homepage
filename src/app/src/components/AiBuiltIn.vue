<template>
  <div id="ai-built-in">
    <HeaderBar :local="true"></HeaderBar>
    <div class="container mx-auto max-w-3xl px-6 py-10">
      <h1 class="text-3xl font-bold mb-2">AI built in</h1>
      <p class="text-lg mb-8">Spend less time on busywork and more on the work only you can do.</p>

      <h2 class="text-xl font-bold mb-2">Spend less time on busywork</h2>
      <p class="mb-8">
        Your tools can draft emails, summarize notes, sort incoming requests and answer common questions, using the
        leading models. Switch models any time without rebuilding.
      </p>

      <h2 class="text-xl font-bold mb-2">Ask your business anything</h2>
      <p class="mb-8">
        Connect your AI assistant through MCP, the open standard assistants use to work with other software.
        Ask it to look up records, add new ones or run tasks, and it acts with your login and only your permissions.
      </p>

      <h2 class="text-xl font-bold mb-2">You stay in control</h2>
      <ul class="list-disc ml-6 mb-10">
        <li class="mb-1">AI sees only what your tools and your login allow.</li>
        <li class="mb-1">You choose the models, and you can switch.</li>
      </ul>

      <va-button color="success" to="/assessment">Get a free assessment</va-button>
    </div>
  </div>
</template>

<script setup>
import HeaderBar from '@trevorism/ui-header-bar'
</script>

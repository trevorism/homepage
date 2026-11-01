<template>
  <div id="ai-built-in">
    <HeaderBar :local="true"></HeaderBar>
    <div class="container mx-auto max-w-3xl px-6 py-10">
      <h1 class="text-3xl font-bold mb-2">AI built in</h1>
      <p class="text-lg mb-8">Focus on your business needs and leave automation of business functions to us.</p>

      <h2 class="text-xl font-bold mb-2">Spend less time on busywork</h2>
      <p class="mb-8">
        We create a seamless integration with the leading AI models and expertly apply their capabilities to add value.
      </p>

      <h2 class="text-xl font-bold mb-2">Agent aware</h2>
      <p class="mb-8">
        Connect your AI agent to our MCP server, the open standard for interfacing with software systems.
        Agents inherit the ability to manage and automate your business processes.
      </p>

      <h2 class="text-xl font-bold mb-2">You stay in control</h2>
      <ul class="list-disc ml-6 mb-10">
        <li class="mb-1">AI agents are granted permissions that you control.</li>
        <li class="mb-1">You choose the models, and can switch anytime.</li>
      </ul>

      <va-button color="success" to="/assessment">Get a free assessment</va-button>
    </div>
  </div>
</template>

<script setup>
import HeaderBar from '@trevorism/ui-header-bar'
</script>

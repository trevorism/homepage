<template>
  <div id="ai-built-in">
    <HeaderBar :local="true"></HeaderBar>
    <div class="container mx-auto max-w-3xl px-6 py-10">
      <h1 class="text-3xl font-bold mb-2">AI built in</h1>
      <p class="text-lg mb-8">Every Trevorism setup can use AI in two ways.</p>

      <h2 class="text-xl font-bold mb-2">Your tools use AI</h2>
      <p class="mb-8">
        The platform's chat service puts models from Anthropic and OpenAI behind one interface. Your tools can draft
        emails, summarize notes, sort incoming requests or answer questions, and you can change models without rebuilding
        anything.
      </p>

      <h2 class="text-xl font-bold mb-2">Your AI assistant works with your business</h2>
      <p class="mb-8">
        Trevorism includes an MCP server, the open standard AI assistants use to connect to other software. Connect an
        assistant such as Claude and it can look up your records, add new ones and run tasks for you, using your login
        and only the permissions you have.
      </p>

      <h2 class="text-xl font-bold mb-2">You stay in control</h2>
      <ul class="list-disc ml-6 mb-10">
        <li class="mb-1">AI sees only what your tools and your login allow.</li>
        <li class="mb-1">You choose the models, and you can switch.</li>
        <li class="mb-1">Like the rest of the platform, it runs hosted by us or in your own Google Cloud account.</li>
      </ul>

      <va-button color="success" to="/assessment">Get a free assessment</va-button>
    </div>
  </div>
</template>

<script setup>
import HeaderBar from '@trevorism/ui-header-bar'
</script>

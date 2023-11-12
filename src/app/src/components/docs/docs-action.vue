<template>
  <div class="grid justify-items-center" id="docs">
    <div class="container px-4">
      <h1 class="text-3xl font-bold -ml-4 mb-2">
        Action
      </h1>
      <h2 class="text-xl font-bold -ml-4 mb-2">
        Motivation
      </h2>
      <div class="mb-2 ml-2">
        <p class="mb-2">The Action API allows users to store REST based endpoint invocations and invoke them by name.
          This provides a convenient way to perform operations on the ecosystem.</p>

      </div>
      <h2 class="text-xl font-bold -ml-4 mb-2">
        API
      </h2>
      <div class="mb-2 ml-2">
        <p class="mb-2">The service is deployed to <a class="text-blue-600 underline dark:text-blue-500 hover:no-underline" href="https://action.trevorism.com">https://action.trevorism.com</a>.</p>
        <p class="mb-2">The interface documentation exists at <a class="text-blue-600 underline dark:text-blue-500 hover:no-underline" href="https://action.trevorism.com/help">https://action.trevorism.com/help</a>.</p>
        <p class="mb-2">The code is available at <a class="text-blue-600 underline dark:text-blue-500 hover:no-underline" href="https://github.com/trevorism/action">https://github.com/trevorism/action</a>.</p>
      </div>
      <h2 class="text-xl font-bold -ml-4 mb-2">
        Usage
      </h2>
      <p class="mb-2">In addition to aliasing requests, the action subdomain hosts several useful APIs.</p>
      <div>
        <ul class="list-disc ml-6">
          <li>Schedule -- for performing tasks on a cadence.
            <a class="text-blue-600 underline dark:text-blue-500 hover:no-underline" href="https://schedule.action.trevorism.com/help">https://schedule.action.trevorism.com</a></li>
          <li>Email -- for emailing.
            <a class="text-blue-600 underline dark:text-blue-500 hover:no-underline" href="https://email.action.trevorism.com/help">https://email.action.trevorism.com</a></li>
          <li>Threshold -- for setting values that trigger another operation.
            <a class="text-blue-600 underline dark:text-blue-500 hover:no-underline" href="https://threshold.action.trevorism.com/help">https://threshold.action.trevorism.com</a></li>
          <li>Chat -- for asking Chat-GPT a question.
            <a class="text-blue-600 underline dark:text-blue-500 hover:no-underline" href="https://chat.action.trevorism.com/help">https://chat.action.trevorism.com</a></li>
        </ul>
      </div>
      <h2 class="text-xl font-bold -ml-4 mb-2">
        Result
      </h2>
      <div class="mb-2 ml-2">
        The Action APIs wraps other web services, converting them into using Trevorism security so users and apps can leverage them.
      </div>
    </div>

  </div>
</template>

<script>

export default {
  name: 'DocsAction',
}
</script>

<style scoped>

</style>

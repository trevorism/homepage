<template>
  <div class="grid justify-items-center" id="docs">
    <div class="container px-4">
      <h1 class="text-3xl font-bold text-center">
        Testing
      </h1>
      <h2 class="text-xl font-bold -ml-4 mb-2">
        Motivation
      </h2>
      <div class="mb-2 ml-2">
        <p class="mb-2">The Testing API enables registration and periodic invocation of software tests.
          One can use tests to validate the ecosystem's functionality and monitor it over time.</p>
      </div>
      <h2 class="text-xl font-bold -ml-4 mb-2">
        API
      </h2>
      <div class="mb-2 ml-2">
        <p class="mb-2">The service is deployed to <a class="text-blue-600 underline dark:text-blue-500 hover:no-underline" href="https://testing.trevorism.com">https://testing.trevorism.com</a>.</p>
        <p class="mb-2">The interface documentation exists at <a class="text-blue-600 underline dark:text-blue-500 hover:no-underline" href="https://testing.trevorism.com/help">https://testing.trevorism.com/help</a>.</p>
        <p class="mb-2">The code is available at <a class="text-blue-600 underline dark:text-blue-500 hover:no-underline" href="https://github.com/trevorism/testing">https://github.com/trevorism/testing</a>.</p>
      </div>
      <h2 class="text-xl font-bold -ml-4 mb-2">
        Usage
      </h2>
      <p class="mb-2">
        When writing a new test; a developer adds it to a test suite, a runnable collection of tests. The Testing API
        allows for registration of test suites. Once registered, one can invoke the test suite via RESTful endpoint.
      </p>
      <p class="mb-2">
        The Testing API currently supports testing suites of junit tests, cucumber, and cypress. We use github actions runners to execute the tests, which provides
        free compute resources ;)
      </p>
      <p class="mb-2">
        The Testing API exposes an error endpoint. Test failures may be posted to this endpoint, and are periodically checked for triage.
      </p>
      <p class="mb-2">
        Combining this API with the functionality Schedule Actions API, one can monitor any service on a cadence.
      </p>
      <h2 class="text-xl font-bold -ml-4 mb-2">
        Result
      </h2>
      <div class="mb-2 ml-2">
        By registering test suites, an inventory of current tests are maintained, which allows ecosystem members to
        discover what test exists, when they are being executed, and what the test results are over time. This enables
        confidence in the ecosystem's functionality.
      </div>
    </div>

  </div>
</template>

<script>

export default {
  name: 'DocsTesting',
}
</script>

<style scoped>

</style>

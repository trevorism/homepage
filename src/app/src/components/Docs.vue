<template>
  <div class="grid justify-items-center" id="docs">
    <HeaderBar :local="true"></HeaderBar>
    <div class="container px-4">
      <h1 class="text-3xl font-bold -ml-4 mb-2">
        Documentation
      </h1>
      <h2 class="text-xl font-bold -ml-4 mb-2">
        Why Trevorism?
      </h2>
      <p class="mb-2">
        Building software systems from scratch is difficult and expensive.
        Trevorism allows developers to bake best practices into applications
        and allows them to evolve over time with pluggable functionality.
      </p>
      <h2 class="text-xl font-bold -ml-4 mb-2">
        How does Trevorism do this?
      </h2>
      <p class="mb-2">
        Software systems share common patterns: User Interfaces, Business Logic, Databases, Security, Logging, Testing, Deployment, etc.
        We bring our extensive experience in building and maintaining applications to enable fast construction of software systems.
        We achieve this by defining a hierarchy of interfaces for each common software system pattern and exposing them as a RESTful API.
        The highest level in the hierarchy is the simplest interface, and each level down unlocks additional functionality.
        Trevorism includes reference implementations of each interface which chooses sensible defaults.
        This allows consumers of Trevorism to rapidly establish common software patterns in their own systems.
      </p>
      <h2 class="text-xl font-bold -ml-4 mb-2">
        What is Trevorism?
      </h2>
      <p class="mb-4">
        Trevorism is a set of distributed web services organized in a lightweight software platform.
        Together, they form a live reference implementation for an enterprise-grade software system.
      </p>
      <div class="flex flex-row mb-4">
        <div class="basis-1/2 justify-items-center mx-2">
          <div class="grid justify-items-center">Trevorism APIs</div>
          <img src="../assets/Trevorism_APIs.png">
        </div>
        <div class="basis-1/2 justify-items-center mx-2">
        <div class="grid justify-items-center">API Interactions</div>
          <img src="../assets/API_Interactions.png">
        </div>

      </div>
      <div class="mb-2 ml-2">
        Users of Trevorism have several options for leveraging these services:
        <ul class="list-disc ml-6">
          <li>Make direct use of the reference implementation</li>
          <li>Pull the code and host it in their environment; all code is open source</li>
          <li>Use one or more interfaces but write a custom implementation</li>
          <li>Create a tenant within Trevorism and get a dedicated instance of the services</li>
        </ul>
      </div>

      <h2 class="text-xl font-bold -ml-4 mb-2">
        Trevorism Achievements
      </h2>
      <div>
        <ul class="list-disc ml-6">
          <li>Open source</li>
          <li>Minimal technology maintenance cost</li>
          <li>One button press for a new production service</li>
          <li>Continuous delivery</li>
          <li>Scale on demand</li>
          <li>Automatic security key rotation</li>
          <li>Synchronous or asynchronous function invocation</li>
          <li>Debug any service locally</li>
          <li>Discoverable inventory</li>
        </ul>
      </div>

    </div>

  </div>
</template>

<script>
import HeaderBar from '@trevorism/ui-header-bar'

export default {
  name: 'Docs',
  components: {HeaderBar}
}
</script>

<style scoped>

</style>

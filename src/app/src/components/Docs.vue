<template>
  <div id="docs">
    <HeaderBar :local="true"></HeaderBar>
    <div>
      <h1>
        Documentation
      </h1>
      <h2>
        Why Trevorism?
      </h2>
      <p>
        Building software systems from scratch is difficult and expensive.
        Trevorism allows developers to bake best practices into applications
        and allows them to evolve over time with pluggable functionality.
      </p>
      <h2>
        How does Trevorism do this?
      </h2>
      <p>
        Software systems share common patterns: User Interfaces, Business Logic, Databases, Security, Logging, Testing, Deployment, etc.
        We bring our extensive experience in building and maintaining applications to enable fast construction of software systems.
        We achieve this by defining a hierarchy of interfaces for each common software system pattern and exposing them as a RESTful API.
        The highest level in the hierarchy is the simplest interface, and each level down unlocks additional functionality.
        Trevorism includes reference implementations of each interface which chooses sensible defaults.
        This allows consumers of Trevorism to rapidly establish common software patterns in their own systems.
      </p>
      <h2>
        What is Trevorism?
      </h2>
      <p>
        Trevorism is a set of distributed web services organized in a lightweight software platform.
        Together, they form a live reference implementation for an enterprise-grade software system.
      </p>
      <va-split>
        <template #start>
          <div class="subtitle" style="text-align: center">Trevorism APIs</div>
          <img src="../assets/Trevorism_APIs.png">
        </template>
        <template #end>
          <div class="subtitle" style="text-align: center">API Interactions</div>
          <img src="../assets/API_Interactions.png">
        </template>
      </va-split>
      <div class="content">
        Users of Trevorism have several options for leveraging these services:
        <ul>
          <li>Make direct use of the reference implementation</li>
          <li>Pull the code and host it in their environment; all code is open source</li>
          <li>Use one or more interfaces but write a custom implementation</li>
          <li>(Coming Soon) Create a tenant within Trevorism and get a dedicated instance of the services</li>
        </ul>
      </div>

      <div class="subtitle">
        Trevorism Achievements
      </div>
      <div class="content">
        <ul>
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

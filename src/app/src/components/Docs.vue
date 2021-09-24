<template>
  <div id="docs">
    <HeaderBar></HeaderBar>
    <div class="container mt-6 mb-6">
      <div class="title mb-6">
        Documentation
      </div>
      <div class="subtitle">
        Why Trevorism?
      </div>
      <div class="content">
        Building software systems from scratch is difficult and expensive.
        Trevorism allows developers to bake best practices into applications
        and allows them to evolve over time with pluggable functionality.
      </div>
      <div class="subtitle">
        How does Trevorism do this?
      </div>
      <div class="content">
        Software systems share common patterns: User Interfaces, Business Logic, Databases, Security, Logging, Testing, Deployment, etc.
        We bring our extensive experience in building and maintaining applications to enable fast construction of software systems.
        We achieve this by defining a hierarchy of interfaces for each common software system pattern.
        The highest level in the hierarchy is the simplest interface, and each level down unlocks additional functionality.
        Trevorism includes reference implementations of each interface which chooses sensible defaults.
        This allows consumers of Trevorism to rapidly establish common software patterns in their own systems.
      </div>
      <div class="subtitle">
        What is Trevorism?
      </div>
      <div class="content">
        Trevorism is a set of distributed web services organized in a lightweight software platform.
        Together, they form a live reference implementation for an enterprise-grade software system.
      </div>
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
    <footer>
      <hr/>
    </footer>
  </div>
</template>

<script>
import HeaderBar from './HeaderBar'

export default {
  name: 'Docs',
  components: {HeaderBar}
}
</script>

<style scoped>

</style>

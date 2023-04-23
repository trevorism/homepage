<template>
  <div class="grid justify-items-center" id="production">
    <HeaderBar :local="true"></HeaderBar>
    <div class="container px-4">
      <h1 class="text-3xl font-bold -ml-4 mb-2">
        Productionalized Web Service
      </h1>

      <p class="mb-2">
        February 2018
      </p>

      <h2 class="text-xl font-bold -ml-4 mb-2">
        What does a complete, productionalized web service look like?
      </h2>

      <div class="mb-2 ml-2">
        <ul class="list-disc ml-6">
          <li>Available over HTTPS</li>
          <li>Unit Tested; Integration Tested; Regression Tested: Code Coverage (>60%)</li>
          <li>Continuously integrated portable build</li>
          <li>Deployable without outages</li>
          <li>Logs can be used to debug transaction failures</li>
          <li>Emits telemetry on its health; telemetry is monitored</li>
          <li>Load Balanced for performance and failover</li>
          <li>Computing resources added on demand</li>
          <li>Hosting and endpoints discoverable</li>
          <li>Artifacts discoverable and backed up</li>
          <li>Access to sensitive data is secured by authentication and authorization</li>
        </ul>
      </div>

    </div>
  </div>
</template>

<script>
import HeaderBar from '@trevorism/ui-header-bar'

export default {
  name: 'production',
  components: {HeaderBar}
}
</script>

<style scoped>

</style>

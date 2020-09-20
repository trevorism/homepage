<template>
  <div id="production">
    <HeaderBar></HeaderBar>
    <div class="container">
      <div class="title mt-6">
        Productionalized Web Service
      </div>

      <div class="content">
        February 2018
      </div>

      <div class="content">
        <h3>What does a complete, productionalized web service look like?</h3>

        <ul>
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
import HeaderBar from '../HeaderBar'

export default {
  name: 'production',
  components: {HeaderBar}
}
</script>

<style scoped>

</style>

<template>
  <div id="trends">
    <HeaderBar :local="true"></HeaderBar>
    <div class="container">
      <div class="title mt-6">
        Technology Trends
      </div>

      <div class="content">
        February 2018
      </div>

      <div class="content">
        <h3>These trends will continue...</h3>

        <ul>
          <li>Computing space and processing power is growing exponentially. (<a
            href="https://singularityhub.com/2016/03/22/technology-feels-like-its-accelerating-because-it-actually-is/">Exponential
            Information Technology Growth</a>)
          </li>
          <li>Hardware and software is getting smaller but more distributed. (Microservices, blockchain, Smart phones)
          </li>
          <li>Generic capabilities will be provided for, only niche software will need to be built. (Cloud)</li>
          <li>Machine to machine interaction is dominating human to machine interaction. (Web APIs, Internet of Things)
          </li>
          <li>Components are getting smarter (Artificial Intelligence / Machine Learning)</li>
        </ul>

        <p>Therefore, in the future we'll have: microscopic, intelligent, distributed, integrated computers.</p>

        <h3>Today:</h3>
        <ul>
          <li>Stay on the leading edge. You do not want to be stuck on the low growth side of the exponential curve.</li>
          <li>Write small, smart, robust services which can be discarded. Keep track of them all. Embrace the challenges
            of distribution.
          </li>
          <li>Learn to leverage the cloud services.</li>
          <li>Don't focus too heavily on GUIs, they are going away.</li>
          <li>Become a AI practitioner.</li>
        </ul>
      </div>
    </div>
    <footer>
      <hr/>
    </footer>
  </div>
</template>

<script>
import HeaderBar from '@trevorism/ui-header-bar'

export default {
  name: 'trends',
  components: {HeaderBar}
}
</script>

<style scoped>

</style>

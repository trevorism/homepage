<template>
  <div id="splash">
    <HeaderBar></HeaderBar>
    <section class="container mt-6">
      <div class="columns">
        <div class="column is-two-fifths">
          <div class="title">Trevorism is a platform for building platforms.</div>

          <div class="card-content subtitle">
            <ul class="mt-3 ml-6" style="list-style-type: circle">
              <li class="mt-3">Open Source</li>
              <li class="mt-3">Scale On Demand</li>
              <li class="mt-3">Built in Security</li>
              <li class="mt-3">Blazing fast speed to market</li>
            </ul>
          </div>
        </div>
        <div class="column is-three-fifths">
          <div style="height:400px">
          <img alt="logo" src="../assets/glassbuilding.jpg" style="height:400px" >
          </div>
        </div>
      </div>
    </section>
    <section class="container">
      <div class="columns">
        <div class="column is-two-fifths">
          <div style="height:350px" class="is-pulled-left">
          <img alt="logo" src="../assets/puzzle.jpg" style="height:350px">
          </div>
        </div>
        <div class="column is-three-fifths mx-6 mt-6">
          <div class="is-size-4">Pluggable microservices architecture enables easy extensibility.</div>

          <div class="is-size-4 mt-6">RESTful APIs simplified for consumption by client libraries, user interfaces, and
            command line interfaces.
          </div>
        </div>
      </div>
    </section>
    <footer>
      <div class="container" >
        <div class="hero-body has-text-centered is-size-3">
          Learn More
          <div class="has-text-centered">
            <span class="is-size-6 mr-6"><router-link to="/">Documentation</router-link></span>
            <span class="is-size-6 ml-6 mr-6"><router-link to="/">Apps</router-link></span>
            <span class="is-size-6 ml-6"><router-link to="/">Contact Us</router-link></span>
          </div>
        </div>

      </div>
    </footer>
  </div>
</template>

<script>
import HeaderBar from './HeaderBar'

export default {
  name: 'Splash',
  components: {HeaderBar}

}
</script>

<style scoped>

</style>

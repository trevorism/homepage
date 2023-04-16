<template>
  <div id="splash">
    <HeaderBar></HeaderBar>
    <section class="glassBackground">
      <div class="container" style="height: 320px">
        <div class="pb-4">&nbsp;</div>
        <div class="card has-background-warning-light" style="width:600px">
          <div class="card-header">
            <div class="is-size-4 mx-6 has-text-weight-semibold">
              Trevorism is a platform for building software ecosystems.
            </div>
          </div>
          <div class="card-content">
            <ul class="ml-6" style="list-style-type: circle">
              <li class="">Blazing fast speed to market</li>
              <li class="mt-2">Scale On Demand</li>
              <li class="mt-2">Built in Security</li>
              <li class="mt-2">Open Source</li>
            </ul>
          </div>
        </div>
      </div>
    </section>
    <section class="puzzleBackground">
      <div class="pb-4">&nbsp;</div>
      <div class="container" style="height: 160px">
        <div class="card has-background-info-light is-pulled-right" style="width:600px">
          <div class="card-header">
            <div class="is-size-4 mx-6 has-text-weight-semibold">
              Balance complexity with elegance.
            </div>
          </div>
          <div class="card-content">
            <ul class="ml-6" style="list-style-type: circle">
              <li class="">Pluggable microservices architecture enables easy extensibility</li>
              <li class="mt-2">Functionality exposed by RESTful APIs to ensure interoperability</li>
              <li class="mt-2">User Interfaces, CLIs, and Client Libraries make using and administering the platform a breeze</li>
            </ul>
          </div>
        </div>
      </div>
      <div class="pb-6">&nbsp;</div>
    </section>
    <footer>
      <div class="container">
        <div class="hero-body has-text-centered is-size-3">
          Learn More
          <div class="has-text-centered">
            <span class="is-size-6 mr-6"><router-link to="/docs">Documentation</router-link></span>
            <span class="is-size-6 ml-6 mr-6"><router-link to="/apps">Apps</router-link></span>
            <span class="is-size-6 ml-6"><router-link to="/contact">Contact Us</router-link></span>
            <span class="is-size-6 ml-6"><a href="https://twitter.com/TrevorismLLC?ref_src=twsrc%5Etfw" class="twitter-follow-button" data-show-count="false">Follow @TrevorismLLC</a></span>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
import HeaderBar from './HeaderBar.vue'

export default {
  name: 'Splash',
  components: {HeaderBar}

}
</script>

<style scoped>
  .glassBackground {
    background-color: #A7DEFA;
    background-image: url("../assets/glassbuildingmed.jpg");
    background-repeat: no-repeat;
    background-position: right;
    background-size: 1280px 720px;
  }
  .puzzleBackground {
    background-color: #391802;
    background-image: url("../assets/puzzlemed.jpg");
    background-repeat: no-repeat;
    background-position: left;
    background-size: 1280px 720px;
  }

</style>

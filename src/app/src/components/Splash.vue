<template>
  <div id="splash">
    <HeaderBar :local="true"></HeaderBar>
    <div class="glassBackground">
      <div class="container" style="height: 220px">
        <va-card color="warning" style="width:600px" stripe>
          <va-card-title>
            Trevorism is a platform for building software ecosystems.
          </va-card-title>
          <va-card-content>
            <ul style="list-style-type: circle">
              <li class="">Blazing fast speed to market</li>
              <li class="mt-2">Scale On Demand</li>
              <li class="mt-2">Built in Security</li>
              <li class="mt-2">Open Source</li>
            </ul>
          </va-card-content>
        </va-card>
      </div>
    </div>
    <div class="puzzleBackground">
      <div class="container" style="height: 220px">
        <va-card color="info" style="width:600px; float:right" stripe>
          <va-card-title>
            Balance complexity with elegance.
          </va-card-title>
          <va-card-content>
            <ul style="list-style-type: circle">
              <li class="">Pluggable microservices architecture enables easy extensibility</li>
              <li class="mt-2">Functionality exposed by RESTful APIs to ensure interoperability</li>
              <li class="mt-2">User Interfaces, CLIs, and Client Libraries make using and administering the platform a breeze</li>
            </ul>
          </va-card-content>
        </va-card>
      </div>
    </div>
    <va-card>
      <va-card-title>
        Learn More
      </va-card-title>
      <va-card-content>
        <div class="row respond">
          <div class="flex flex-col xs1 offset-md1"><router-link to="/docs">Documentation</router-link></div>
          <div class="flex flex-col xs1 offset-md1"><router-link to="/apps">Apps</router-link></div>
          <div class="flex flex-col xs1 offset-md1"><router-link to="/contact">Contact</router-link></div>
          <div class="flex flex-col xs1 offset-md1"><a href="https://twitter.com/TrevorismLLC?ref_src=twsrc%5Etfw" class="twitter-follow-button" data-show-count="false">@TrevorismLLC</a></div>
        </div>
      </va-card-content>
    </va-card>
  </div>
</template>

<script>
import HeaderBar  from "@trevorism/ui-header-bar";

export default {
  name: 'Splash',
  components: {HeaderBar}

}
</script>

<style>
.va-dropdown__content{
  z-index: 1500;
}
</style>

<style scoped>


  @media(max-width: 768px) {
    .respond{
      display:block;
    }
  }

  .container {
    padding: 50px;
  }

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

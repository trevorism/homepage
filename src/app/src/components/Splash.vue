<template>
  <div id="splash">
    <HeaderBar :local="true"></HeaderBar>
    <div class="bg-scroll glassBackground">
      <div class="container" style="height: 280px">
        <va-card color="warning" style="width:600px" stripe>
          <va-card-title>
            Trevorism is a platform for building software ecosystems.
          </va-card-title>
          <va-card-content>
            <ul class="ml-6" style="list-style-type: circle">
              <li class="">Blazing fast speed to market</li>
              <li class="mt-2">Scale On Demand</li>
              <li class="mt-2">Built in Security</li>
              <li class="mt-2">Open Source</li>
            </ul>
          </va-card-content>
        </va-card>
      </div>
    </div>
    <div class="bg-scroll puzzleBackground">
      <div class="container" style="height: 280px">
        <va-card color="info" style="width:600px; float:right" stripe>
          <va-card-title>
            Balance complexity with elegance.
          </va-card-title>
          <va-card-content>
            <ul class="ml-6" style="list-style-type: circle">
              <li class="">Pluggable microservices architecture enables easy extensibility</li>
              <li class="mt-2">Functionality exposed by RESTful APIs to ensure interoperability</li>
              <li class="mt-2">User Interfaces, CLIs, and Client Libraries make using and administering the platform a breeze</li>
            </ul>
          </va-card-content>
        </va-card>
      </div>
    </div>
    <div class="text-xl grid justify-items-center">
      Learn More
    </div>
    <hr/>
    <div class="flex flex-row ">
      <va-chip flat class="grid justify-items-center basis-1/4" to="/docs">Documentation</va-chip>
      <va-chip flat class="grid justify-items-center basis-1/4" to="/apps">Apps</va-chip>
      <va-chip flat class="grid justify-items-center basis-1/4" to="/contact">Contact</va-chip>
      <va-chip flat class="grid justify-items-center basis-1/4" href="https://twitter.com/TrevorismLLC?ref_src=twsrc%5Etfw">Follow @TrevorismLLC</va-chip>
    </div>

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

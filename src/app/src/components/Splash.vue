<template>
  <div id="splash">
    <HeaderBar :local="true"></HeaderBar>
    <div class="bg-scroll glassBackground">
      <div class="container flex items-center justify-center" style="height: 280px">
        <va-card color="warning" class="w-3/5 max-h-48" stripe>
          <va-card-title>
            Develop your software ecosystem with Trevorism
          </va-card-title>
          <va-card-content>
            <ul class="ml-6" style="list-style-type: circle">
              <li class="">Blazing fast speed to market</li>
              <li class="mt-1">Scale On Demand</li>
              <li class="mt-1">Built in Security</li>
              <li class="mt-1">Open Source</li>
            </ul>
          </va-card-content>
        </va-card>
      </div>
    </div>
    <div class="bg-scroll puzzleBackground">
      <div class="container flex items-center justify-center" style="height: 280px">
        <va-card color="info" class="w-3/5" stripe>
          <va-card-title>
            Balance complexity with elegance
          </va-card-title>
          <va-card-content>
            <ul class="ml-6" style="list-style-type: circle">
              <li class="">Pluggable microservices architecture enables easy extensibility</li>
              <li class="mt-1">All functionality exposed by RESTful APIs to ensure interoperability</li>
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
import HeaderBar from "@trevorism/ui-header-bar";

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
  .glassBackground {
    background-color: #D9E7F2;
    background-image: url("../assets/glassbuildingmed.jpg");
    background-repeat: no-repeat;
    background-position: right;
    background-size: 1280px 720px;
  }
  .puzzleBackground {
    background-color: #222A2B;
    background-image: url("../assets/puzzlemed.jpg");
    background-repeat: no-repeat;
    background-position: left;
    background-size: 1280px 720px;
  }
</style>

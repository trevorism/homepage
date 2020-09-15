<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
import HeaderBar from './components/HeaderBar.vue'

export default {
  name: 'App',
  components: {HeaderBar}
}
</script>

<style>
#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
</style>

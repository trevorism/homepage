<template>
<div id="splash">
  <HeaderBar></HeaderBar>
  Splash content
  {{cookie}}
</div>
</template>

<script>
import HeaderBar from './HeaderBar'
export default {
  name: 'Splash',
  components: {HeaderBar},
  data () {
    return {
      "cookie": ""
    }
  },
  mounted() {
    this.cookie = document.cookie
  }

}
</script>

<style scoped>

</style>

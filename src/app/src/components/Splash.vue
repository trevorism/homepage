<template>
<div id="splash">
  <HeaderBar></HeaderBar>
  Splash content
</div>
</template>

<script>
import HeaderBar from './HeaderBar'
export default {
  name: 'Splash',
  components: {HeaderBar},
  data () {
    return {
      'cookie': ''
    }
  }

}
</script>

<style scoped>

</style>

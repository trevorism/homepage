<template>
  <div id="admin">
    <HeaderBar></HeaderBar>
    Only accessible to logged in users
  </div>
</template>

<script>
import HeaderBar from './HeaderBar'

export default {
  name: 'Admin',
  components: {
    HeaderBar
  }
}
</script>

<style scoped>

</style>

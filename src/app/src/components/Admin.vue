<template>
  <div id="admin">
    <HeaderBar></HeaderBar>
    <div id="adminText">Only accessible to logged-in Admin users</div>
  </div>
</template>

<script>
import HeaderBar from './HeaderBar.vue'

export default {
  name: 'Admin',
  components: {
    HeaderBar
  }
}
</script>

<style scoped>

</style>

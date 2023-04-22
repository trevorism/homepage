<template>
  <div id="admin">
    <HeaderBar :local="true"></HeaderBar>
    <div id="adminText">Only accessible to logged-in Admin users</div>
  </div>
</template>

<script>
import HeaderBar from '@trevorism/ui-header-bar'

export default {
  name: 'Admin',
  components: {
    HeaderBar
  }
}
</script>

<style scoped>

</style>

<template>
  <div class="grid justify-items-center" id="admin">
    <HeaderBar :local="true"></HeaderBar>
    <div id="adminText">Only accessible to logged-in Admin users</div>
  </div>
</template>

<script setup>
import HeaderBar from '@trevorism/ui-header-bar'
</script>

<style scoped>

</style>

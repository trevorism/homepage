<template>
  <div id="apps">
    <HeaderBar :local="true"></HeaderBar>
    <div class="container mt-6 mb-6">
      <div class="title mb-6">
        Apps
      </div>
      <div class="subtitle">
        This page shows various applications made by Trevorism
      </div>
    </div>
  </div>
</template>

<script>
import HeaderBar from '@trevorism/ui-header-bar'

export default {
  name: 'Apps',
  components: {HeaderBar}
}
</script>

<style scoped>

</style>

<template>
  <div id="notfound">
    <HeaderBar :local="true"></HeaderBar>
    <h1 class="text-3xl font-bold ml-4 mb-2">
      Not Found
    </h1>
    <p class="ml-4 mb-2">
      404 -- Page not found
    </p>
  </div>
</template>

<script>
import HeaderBar from '@trevorism/ui-header-bar'

export default {
  name: 'NotFound',
  components: {HeaderBar}
}
</script>

<style scoped>

</style>

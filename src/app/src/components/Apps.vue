<template>
  <div id="apps">
    <HeaderBar :local="true"></HeaderBar>
    <h1 class="text-3xl font-bold ml-4 mb-2">
        Apps
    </h1>
    <p class="ml-4 mb-2">
        This following applications were built using Trevorism.
    </p>
    <ul class="list-disc ml-10">
      <li><va-chip class="w-1/6" flat href="https://timeline.draw.trevorism.com">Timeline Generator</va-chip> Create a downloadable color coded timeline with pixel perfect scale</li>
      <li class="mt-2"><va-chip class="w-1/6" flat href="https://active.project.trevorism.com">Service Registry</va-chip> View a list of all Trevorism integrated services and libraries</li>
      <li class="mt-2"><va-chip class="w-1/6" flat href="https://prompt.action.trevorism.com/">Ask a Question</va-chip> Ask questions of other Trevorism users and get answers</li>

    </ul>

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

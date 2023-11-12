<script setup>

import HeaderBar from "@trevorism/ui-header-bar";
import DocsLayout from "./docs-layout.vue";

const values = [
  {
    title: "Introduction",
    icon: "home",
  },
  {
    title: "Achievements",
    icon: "grade",
  },
  {
    title: "Info",
    icon: "info",
  },
  {
    title: "Auth",
    icon: "security",
  },
  {
    title: "Data",
    icon: "storage",
  },

];
</script>

<template>
  <div id="layout-caller">
    <HeaderBar :local="true"></HeaderBar>
    <docs-layout :headers=values></docs-layout>
  </div>
</template>

<style scoped>

</style>
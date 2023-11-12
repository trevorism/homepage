<template>
  <div id="docsLayout">
    <VaLayout :style="getMenuHeight()">
      <template #left>
        <VaSidebar width="11rem">
          <VaSidebarItem v-for="(header, index) in headers" :key="header.title" @click="show(index+1)">
            <VaSidebarItemContent>
              <VaIcon v-if="header.icon" :name=header.icon />
              <VaSidebarItemTitle>
                {{ header.title }}
              </VaSidebarItemTitle>
            </VaSidebarItemContent>
          </VaSidebarItem>
        </VaSidebar>
      </template>

      <template #content>

        <main class="p-4">
          <div :style="getDisplay(1)"> <docs-intro></docs-intro></div>
          <div :style="getDisplay(2)"> <docs-achievements></docs-achievements></div>
          <div :style="getDisplay(3)"> <docs-info></docs-info></div>
          <div :style="getDisplay(4)"> <docs-auth></docs-auth></div>
          <div :style="getDisplay(5)"> <docs-data></docs-data></div>
          <div :style="getDisplay(6)"> <docs-action></docs-action></div>
        </main>
      </template>
    </VaLayout>
  </div>
</template>

<script>
import DocsIntro from "../docs/docs-intro.vue";
import DocsAchievements from "../docs/docs-achievements.vue";
import DocsInfo from "../docs/docs-info.vue";
import DocsAuth from "../docs/docs-auth.vue";
import DocsData from "../docs/docs-data.vue";
import DocsAction from "../docs/docs-action.vue";

export default {
  name: "DocsLayout",
  components: {DocsIntro, DocsAchievements, DocsInfo, DocsAuth, DocsData, DocsAction},
  props: {
    headers: {
      type: Array,
      required: true
    }
  },
  data () {
    return {
      cardVisible: 1,
      height: window.innerHeight - 70,
      loading: true
    }
  },
  mounted() {
    this.cardVisible = 1
    window.addEventListener("resize", this.updateMenuHeight);
    this.updateMenuHeight()
  },
  unmounted() {
    window.removeEventListener("resize", this.updateMenuHeight);
  },
  methods: {
    show: function (num) {
      this.cardVisible = num
    },
    getDisplay: function (num) {
      if(this.cardVisible === num){
        return 'display: block'
      } else {
        return 'display: none'
      }
    },
    getMenuHeight: function () {
      return 'height: ' + (this.height) + 'px'
    },
    updateMenuHeight: function () {
      this.height = window.innerHeight - 70
    }
  }
}
</script>

<style scoped>

</style>

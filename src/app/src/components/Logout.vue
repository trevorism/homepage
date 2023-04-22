<template>
  <div id="logout">
    <HeaderBar ref="headerBarIsm"></HeaderBar>
    <div class="container mt-6 mb-6">
      <div class="title mb-6">
        Log out of Trevorism
      </div>
      <div class="subtitle">
        {{ message }}
      </div>
    </div>
    <b-loading :is-full-page="false" v-model:active="disabled" :can-cancel="false"></b-loading>
  </div>
</template>

<script>
import HeaderBar from '@trevorism/ui-header-bar'
import axios from 'axios'

export default {
  name: 'Logout',
  components: {HeaderBar},
  data () {
    return {
      message: '',
      disabled: false
    }
  },
  mounted () {
    let self = this
    this.disabled = true
    axios.post('api/logout')
      .then(() => {
        self.disabled = false
        self.$refs.headerBarIsm.checkAuthenticated()
        self.message = 'Bye!'
      })
      .catch(() => {
        self.disabled = false
        self.message = 'Logout failed! Close your browser and clear cookies to be safe.'
      })
  }
}
</script>

<style scoped>

</style>

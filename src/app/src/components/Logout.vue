<template>
  <div class="grid justify-items-center" id="logout">
    <HeaderBar :local="true"></HeaderBar>
    <div class="container px-4">
      <h2 class="text-xl font-bold -ml-4 mb-2">
        Log out of Trevorism
      </h2>
      <div class="mb-2 ml-2">
        {{ message }}
      </div>
    </div>
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

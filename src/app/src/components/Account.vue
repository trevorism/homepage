<template>
  <div class="grid justify-items-center" id="account">
    <HeaderBar :local="true"></HeaderBar>
    <div class="container px-4">
      <h1 class="text-2xl font-bold -ml-4 mb-2">Trevorism Profile</h1>

      <va-list>
        <va-list-item>
        <va-list-item-section>
          <va-list-item-label class="text-2xl">
            {{ user.username }}
          </va-list-item-label>

          <va-list-item-label caption class="text-1xl">
            {{ user.email }}
          </va-list-item-label>
        </va-list-item-section>
        <va-list-item-section icon v-if="user.admin">
          <va-popover message="Administrator">
            <va-icon class="material-icons" name="admin_panel_settings" />
          </va-popover>
        </va-list-item-section>
        </va-list-item>
      </va-list>
      <va-chip flat class="grid justify-items-center basis-1/4" to="/change">Change Password</va-chip>
    </div>
  </div>
</template>

<script>
import HeaderBar from '@trevorism/ui-header-bar'
import axios from 'axios'

export default {
  name: "Account",
  components: {HeaderBar},
  data () {
    return {
      user: {},
      loading: true
    }
  },
  mounted () {
    axios.get('api/user')
      .then(response => {
        this.user = response.data
        this.loading = false
      })
  }
}
</script>

<style scoped>

</style>

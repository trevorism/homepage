<template>
  <div class="grid justify-items-center" id="account">
    <HeaderBar :local="true"></HeaderBar>
    <div class="container px-4">
      <h1 class="text-2xl font-bold -ml-4 mb-2">Trevorism Profile</h1>

      <div class="flex items-center">
        <VaFileUpload v-model="basic"
                      hide-file-list
                      file-types="jpg,png,jpeg,gif"
                      @fileAdded="handleFileAdded">
          <va-inner-loading :loading="uploading">
            <img :src="profileImage" class="rounded-full w-24 h-24" alt="Profile Image"/>
          </va-inner-loading>
        </VaFileUpload>
        <va-list class="ml-4">
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
                <va-icon class="material-icons" name="admin_panel_settings"/>
              </va-popover>
            </va-list-item-section>
          </va-list-item>
        </va-list>

      </div>
      <va-chip flat class="grid justify-items-center basis-1/4" to="/change">Change Password</va-chip>
    </div>

  </div>
</template>

<script>
import HeaderBar from '@trevorism/ui-header-bar'
import axios from 'axios'
import {useCookies} from "vue3-cookies";


export default {
  name: "Account",
  components: {HeaderBar},
  data() {
    return {
      user: {},
      basic: [],
      loading: true,
      uploading: false,
      profileImage: 'https://via.placeholder.com/150'
    }
  },
  mounted() {
    const {cookies} = useCookies();
    const username = cookies.get('user_name')

    axios.get('api/user')
        .then(response => {
          this.user = response.data
          this.loading = false
        })
    axios.get('api/image/' + username + '/profile')
        .then(response => {
          const bytes = response.data
          const imageSrc = 'data:image/png;base64,' + btoa(bytes)
          this.profileImage = imageSrc
        }).catch(error => {
    });
  },
  methods: {
    handleFileAdded(file) {
      this.uploading = true
      const formData = new FormData();
      formData.append('file', file[0]);
      axios.post('api/image', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then(response => {
        axios.get('api/image/' + response.data)
            .then(response => {
              const bytes = response.data
              const imageSrc = 'data:image/png;base64,' + btoa(bytes)
              this.profileImage = imageSrc
              this.uploading = false
            }).catch(error => {
          this.uploading = false
        });
      })
    }
  }
}
</script>

<style scoped>

</style>

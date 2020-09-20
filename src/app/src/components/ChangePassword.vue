<template>
  <div id="change">
    <HeaderBar></HeaderBar>
    <section class="container formWidth">
      <div class="is-size-4 mt-6 mb-6">Change Password on Trevorism</div>
      <form class="loginBorder">
        <div class="mx-4 mt-4 mb-4">
          <b-field label="Username">
            <b-input type="text"
                     required
                     validation-message="Must be at least 3 characters"
                     minlength="3"
                     v-model="username"
                     :autofocus="true">
            </b-input>
          </b-field>
          <b-field label="Current Password">
            <b-input type="password"
                     required
                     validation-message="Must be at least 6 characters"
                     minlength="6"
                     v-model="currentPassword"></b-input>
          </b-field>
          <b-field label="New Password">
            <b-input type="password"
                     required
                     validation-message="Must be at least 6 characters"
                     minlength="6"
                     v-model="newPassword">
            </b-input>
          </b-field>
          <b-field label="Repeat New Password">
            <b-input type="password"
                     required
                     validation-message="Must be at least 6 characters and match the new password"
                     minlength="6"
                     v-model="repeatPassword">
            </b-input>
          </b-field>
          <div class="is-centered has-text-centered">
            <button class="button is-primary" :disabled="disabled" @click="invokeButton">
              Submit
            </button>
            <b-loading :is-full-page="false" :active.sync="disabled" :can-cancel="false"></b-loading>
          </div>
        </div>
      </form>
      <div class="has-text-centered has-text-danger">{{errorMessage}}</div>
    </section>
  </div>
</template>

<script>
import HeaderBar from './HeaderBar'
import axios from 'axios'

export default {
  name: 'ChangePassword',
  components: {HeaderBar},
  data () {
    return {
      username: '',
      currentPassword: '',
      newPassword: '',
      repeatPassword: '',
      errorMessage: '',
      disabled: false
    }
  },
  methods: {
    invokeButton: function () {
      let self = this

      if (this.newPassword !== this.repeatPassword) {
        this.errorMessage = 'The new and repeat passwords do not match'
        return
      }

      let request = {
        username: this.username,
        currentPassword: this.currentPassword,
        desiredPassword: this.repeatPassword
      }
      this.disabled = true
      axios.post('api/user/change', request)
        .then(() => {
          this.disabled = false
          this.clearFields()
          self.$router.push('/profile')
        })
        .catch(() => {
          this.errorMessage = 'Unable to change password'
          this.disabled = false
        })
    },
    clearFields: function () {
      this.username = ''
      this.currentPassword = ''
      this.desiredPassword = ''
      this.repeatPassword = ''
    }
  }
}
</script>

<style scoped>
  .loginBorder {
    border: #dddddd 1px solid;
    background: #efefef;
  }
  .formWidth {
    width: 400px
  }
</style>

<template>
  <div id="register">
    <HeaderBar :local="true"></HeaderBar>
    <section class="container formWidth">
      <div class="is-size-4 mt-6 mb-6">Register for Trevorism</div>
      <form class="loginBorder">
        <div class="mx-4 mt-4 mb-4">
          <va-input type="text"
                    required
                    error-messages="Must be at least 3 characters"
                    label="Username"
                    minlength="3"
                    v-model="username"
                    :autofocus="true">
          </va-input>
          <va-input type="email"
                    required
                    label="Email"
                    v-model="email">
          </va-input>
          <va-input type="password"
                    required
                    error-messages="Must be at least 6 characters"
                    label="Password"
                    minlength="6"
                    v-model="newPassword">
          </va-input>
          <va-input type="password"
                    required
                    error-messages="Must be at least 6 characters and match the new password"
                    label="Repeat Password"
                    minlength="6"
                    v-model="repeatPassword">
          </va-input>
          <div class="is-centered has-text-centered">
            <button class="button is-primary" :disabled="disabled" @click="invokeButton">
              Submit
            </button>
          </div>
        </div>
      </form>
      <div v-if="successMessage !== ''" class="has-text-centered">{{successMessage}}
        <router-link class="is-info ml-4" to="/">Home</router-link>
      </div>
      <div class="has-text-centered has-text-danger">{{errorMessage}}</div>
    </section>
  </div>
</template>

<script>
import HeaderBar from '@trevorism/ui-header-bar'
import axios from 'axios'

export default {
  name: 'register',
  components: {HeaderBar},
  data () {
    return {
      username: '',
      email: '',
      newPassword: '',
      repeatPassword: '',
      errorMessage: '',
      successMessage: '',
      disabled: false
    }
  },
  methods: {
    invokeButton: function () {
      let request = {
        username: this.username,
        email: this.email,
        password: this.repeatPassword
      }
      this.disabled = true
      axios.post('api/user', request)
        .then(() => {
          this.disabled = false
          this.clearFields()
          this.errorMessage = ''
          this.successMessage = 'Registration success! An email will be sent when your registration is activated.'
        })
        .catch(() => {
          this.errorMessage = 'Unable to complete your registration'
          this.successMessage = ''
          this.disabled = false
        })
    },
    clearFields: function () {
      this.username = ''
      this.email = ''
      this.newPassword = ''
      this.repeatPassword = ''
    }
  }
}
</script>

<style scoped>
  .loginBorder{
    border: #dddddd 1px solid;
    background: #efefef;
  }
  .formWidth {
    width: 400px
  }
</style>

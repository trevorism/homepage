<template>
  <section id="forgot" class="container formWidth">
    <div class="has-text-centered is-centered mt-6 mb-6">
      <router-link to="/"><img alt="logo" src="../assets/TrevorismLogoWhite.png"></router-link>
    </div>
    <div class="is-size-4 mt-6 mb-6">Forgot Password on Trevorism</div>
    <div class="is-size-6  mt-6 mb-6">We will send you a link to reset your password.</div>
    <form class="loginBorder">
      <div class="mx-4 mt-4 mb-4">
        <b-field label="Email Address">
          <b-input
            type="email"
            required
            v-model="email"
            :autofocus="true">
          </b-input>
        </b-field>

        <div class="is-centered has-text-centered">
          <button class="button is-info" :disabled="disabled" @click="invokeButton">
            Submit
            <b-loading :is-full-page="false" :active.sync="disabled" :can-cancel="false"></b-loading>
          </button>
        </div>
      </div>
    </form>
    <div v-if="successMessage !== ''" class="has-text-centered">{{successMessage}}
      <router-link class="is-info ml-4" to="/login">Login</router-link>
    </div>
    <div class="has-text-centered has-text-danger">{{errorMessage}}</div>
  </section>
</template>

<script>
import axios from 'axios'

export default {
  name: 'ForgotPassword',
  data () {
    return {
      email: '',
      disabled: false,
      errorMessage: '',
      successMessage: ''
    }
  },
  methods: {
    invokeButton: function () {
      let request = {
        email: this.email
      }
      this.disabled = true
      axios.post('api/login/forgot', request)
        .then(() => {
          this.disabled = false
          this.errorMessage = ''
          this.successMessage = 'Email sent successfully!'
        })
        .catch(() => {
          this.errorMessage = 'Unable to find the email address'
          this.successMessage = ''
          this.disabled = false
        })
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

<template>
  <header-bar :local=true></header-bar>
  <section id="login" class="container formWidth">
    <div class="has-text-centered is-centered mt-6 mb-6">
      <router-link to="/"><img alt="logo" src="../assets/TrevorismLogoWhite.png"></router-link>
    </div>
    <div class="is-size-4 mt-6 mb-6">Login to Trevorism</div>
    <div class="loginLink is-pulled-right mr-4">
      <router-link class="has-text-info" tabindex="-1" to="/forgot">Forgot Password?</router-link>
    </div>
    <form class="loginBorder">
      <div class="mx-4 mt-4 mb-4">
        <b-field label="Username">
          <b-input
            type="text"
            required
            validation-message="Must be at least 3 characters"
            minlength="3"
            v-model="username"
            :autofocus="true">
          </b-input>
        </b-field>
        <b-field label="Password">
          <b-input type="password"
                   required
                   validation-message="Must be at least 6 characters"
                   minlength="6"
                   v-model="password">
          </b-input>
        </b-field>
        <div class="is-centered has-text-centered">
          <button class="button is-primary" :disabled="disabled" @click="invokeButton">
            Submit
            <b-loading :is-full-page="false" v-model:active="disabled" :can-cancel="false"></b-loading>
          </button>
        </div>
      </div>
    </form>
    <div class="has-text-centered has-text-danger">{{errorMessage}}</div>

  </section>
</template>

<script>
import HeaderBar from '@trevorism/ui-header-bar'
import axios from 'axios'

export default {
  name: 'Login',
  components: {HeaderBar},
  data () {
    return {
      username: '',
      password: '',
      errorMessage: '',
      disabled: false
    }
  },
  mounted () {
    axios.get('api/authWarmup')
  },
  methods: {
    invokeButton: function () {
      let self = this
      let request = {
        username: this.username,
        password: this.password
      }
      this.disabled = true
      axios.post('api/login', request)
        .then(() => {
          this.disabled = false
          this.clear()
          let returnUrl = self.$route.query.return_url
          if (returnUrl) {
            window.location.href = returnUrl
          } else {
            self.$router.push('/')
          }
        })
        .catch(() => {
          this.errorMessage = 'Unable to login'
          this.clear()
          this.disabled = false
        })
    },
    clear: function () {
      this.username = ''
      this.password = ''
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

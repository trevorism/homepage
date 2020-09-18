<template>
<section id="login" class="container">
  <router-link to="/"><img alt="logo" src="../assets/TrevorismLogoWhite.png"></router-link>
  <h1 class="title is-4 vertSpacer">Login to Trevorism</h1>
  <form class="loginBorder">
    <div class="marginSpacer">
      <label class="loginLabel">Username</label>
      <b-field>
        <b-input v-model="username" :autofocus="true"></b-input>
      </b-field>
      <div>
        <label class="loginLabel">Password</label>
        <div class="loginLink">
          <router-link tabindex="-1" to="/forgot">Forgot Password</router-link>
        </div>
      </div>
      <b-field>
        <b-input type="password" v-model="password"></b-input>
      </b-field>
      <button class="button is-primary" :disabled="disabled" @click="invokeButton">
        Submit
        <b-loading :is-full-page="false" :active.sync="disabled" :can-cancel="false"></b-loading>
      </button>
    </div>
  </form>
</section>
</template>

<script>
import Header from './HeaderBar'
import axios from 'axios'

export default {
  name: 'Login',
  components: {Header},
  data () {
    return {
      username: '',
      password: '',
      disabled: false
    }
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

          let returnUrl = self.$route.query.return_url
          if (returnUrl) {
            window.location.href = returnUrl
          } else {
            self.$router.push('/')
          }
        })
        .catch(() => {
          this.disabled = false
        })
    }
  }
}
</script>

<style scoped>
  #login{
    text-align: center;
    margin-top: 60px;
    width: 600px;
  }
  .vertSpacer {
    margin-top: 60px;
    margin-bottom: 60px;
  }
  .loginBorder{
    border: #dddddd 1px solid;
    background: #efefef;
  }
  .marginSpacer {
    margin: 20px;
  }
  .loginLabel {
    float: left;
    margin-left: 5px;
    font-weight: bold;
  }
  .loginLink{
    float: right;
    margin-right: 5px;
  }

</style>

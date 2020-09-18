<template>
  <section id="forgot" class="container">
    <router-link to="/"><img alt="logo" src="../assets/TrevorismLogoWhite.png"></router-link>
    <h1 class="title is-4 vertSpacer">Forgot Password</h1>
    <h2 class="title is-6 vertSpacer">We will send you a link to reset your password.</h2>
    <form class="loginBorder">
      <div class="marginSpacer">
        <label class="loginLabel">Email Address</label>
        <b-field>
          <b-input v-model="email" :autofocus="true"></b-input>
        </b-field>
        <button class="button is-info" :disabled="disabled" @click="invokeButton">
          Submit
          <b-loading :is-full-page="false" :active.sync="disabled" :can-cancel="false"></b-loading>
        </button>
      </div>
    </form>
  </section>
</template>

<script>
import axios from 'axios'

export default {
  name: 'ForgotPassword',
  data () {
    return {
      email: '',
      disabled: false
    }
  },
  methods: {
    invokeButton: function () {
      let self = this
      let request = {
        email: this.email
      }
      this.disabled = true
      axios.post('api/login/forgot', request)
        .then(() => {
          this.disabled = false
          self.$router.push('/')
        })
        .catch(() => {
          this.disabled = false
        })
    }
  }

}
</script>

<style scoped>
  #forgot{
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

</style>

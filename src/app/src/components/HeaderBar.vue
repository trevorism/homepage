<template>
  <div id="header">
    <b-navbar>
      <template slot="brand">
        <b-navbar-item tag="router-link" :to="{ path: '/' }">
          <img src="favicon.ico">
        </b-navbar-item>
      </template>
      <template slot="start">
        <b-navbar-item href="#">
          Contact
        </b-navbar-item>
        <b-navbar-item href="#">
          Careers
        </b-navbar-item>

      </template>

      <template slot="end">
        <div v-if="!authenticated">
          <b-navbar-item tag="div">
            <div class="buttons">
              <a class="button is-primary">
                <strong>Sign up</strong>
              </a>
              <router-link class="button is-light" to="/login">Login</router-link>
            </div>
          </b-navbar-item>
        </div>
        <div v-else>
          <b-navbar-item tag="div">
            <div class="buttons">
              <a @click="logout()" class="button is-primary">Logout</a>
            </div>
            Welcome {{username}}
          </b-navbar-item>
        </div>
      </template>

    </b-navbar>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Header',
  data () {
    return {
      authenticated: false,
      username: ''
    }
  },
  methods: {
    checkAuthenticated: function () {
      let session = this.$cookies.get('session')
      let self = this
      this.authenticated = !!session

      axios.get('api/user', { 'headers': { 'Authorization': 'bearer ' + session } })
        .then((val) => {
          self.username = val.data.username
        })
        .catch((val) => {
          this.username = 'error'
        })
    },
    logout: function () {
      let self = this
      axios.post('api/logout')
        .then(() => {
          console.log('Here, successes')
          self.$cookies.remove('session')
          self.checkAuthenticated()
        })
        .catch(() => {
          console.log('Here, error')
        })
    }
  },
  mounted () {
    this.checkAuthenticated()
  }
}
</script>

<style scoped>

</style>

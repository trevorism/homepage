<template>
  <div id="header">
    <b-navbar class="navbar navbar-background">
      <template slot="brand">
        <b-navbar-item tag="router-link" :to="{ path: '/' }">
          <img src="../assets/TrevorismLogo.png">
        </b-navbar-item>
      </template>
      <template slot="start">
        <b-navbar-dropdown label="Apps">
          <b-navbar-item href="http://registry.datastore.trevorism.com">
            Service Registry
          </b-navbar-item>
          <b-navbar-item href="https://click.trevorism.com">
            Click
          </b-navbar-item>
        </b-navbar-dropdown>

        <b-navbar-dropdown label="Articles">
          <b-navbar-item href="/admin">
            Admin
          </b-navbar-item>
        </b-navbar-dropdown>

        <b-navbar-item href="#">
          Contact
        </b-navbar-item>
        <b-navbar-item href="#">
          Careers
        </b-navbar-item>
        <b-navbar-item href="/admin" v-if="admin">
          Admin
        </b-navbar-item>
      </template>

      <template slot="end">
        <div v-if="!authenticated">
          <b-navbar-item tag="div">
            <div class="buttons">
              <a class="button is-primary">
                Register
              </a>
              <router-link class="button is-light" to="/login">Login</router-link>
            </div>
          </b-navbar-item>
        </div>
        <div v-else>
          <b-navbar-dropdown class="navHeight" :label="username">
            <b-navbar-item href="/account">
              Account
            </b-navbar-item>
            <b-navbar-item tag="div">
              <a @click="logout()" class="button is-primary">Logout</a>
            </b-navbar-item>
          </b-navbar-dropdown>
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
      username: '',
      admin: false
    }
  },
  methods: {
    checkAuthenticated: function () {
      this.username = this.$cookies.get('user_name')
      this.admin = this.$cookies.get('admin') === 'true'
      this.authenticated = !!this.username
    },
    logout: function () {
      let self = this
      axios.post('api/logout')
        .then(() => {
          self.$cookies.remove('user_name')
          self.$cookies.remove('admin')
          this.checkAuthenticated()
          self.$router.push('/').catch(() => {
          })
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
.navbar-background {
  background: black;
}

.navHeight {
  height: 52px;
}
</style>

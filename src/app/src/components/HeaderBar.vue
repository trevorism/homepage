<template>
  <div id="header">
    <b-navbar class="navbar has-background-black">
      <template slot="brand">
        <b-navbar-item tag="router-link" :to="{ path: '/' }">
          <img src="../assets/TrevorismLogo.png">
        </b-navbar-item>
      </template>
      <template slot="start">
        <b-navbar-dropdown label="Apps">
          <b-navbar-item href="https://www.trevorism.com">
            Home
          </b-navbar-item>
          <b-navbar-item href="https://click.trevorism.com">
            Click
          </b-navbar-item>
          <b-navbar-item href="https://active.project.trevorism.com">
            Service Registry
          </b-navbar-item>
        </b-navbar-dropdown>

        <b-navbar-dropdown label="Articles">
          <b-navbar-item href="/docs">
            Trevorism Documentation
          </b-navbar-item>
          <b-navbar-item href="/articles/prototype">
            Prototype Driven Development 05/2013
          </b-navbar-item>
          <b-navbar-item href="/articles/trends">
            Technology Trends 02/2018
          </b-navbar-item>
          <b-navbar-item href="/articles/production">
            Productionalized Service 02/2018
          </b-navbar-item>
          <b-navbar-item href="/articles/improvement">
            Scientific Improvement 12/2020
          </b-navbar-item>
        </b-navbar-dropdown>

        <b-navbar-item href="/contact">
          Contact
        </b-navbar-item>

        <b-navbar-dropdown label="Tools" v-if="authenticated">
          <b-navbar-item href="https://github.com/trevorism">
            Github
          </b-navbar-item>
          <b-navbar-item href="https://kanbanflow.com/board/a6a2c3aa67d9492ac64007975f9f322a">
            Kanban Flow
          </b-navbar-item>
          <b-navbar-item href="https://www.npmjs.com/search?q=%40trevorism">
            NPM
          </b-navbar-item>
          <b-navbar-item href="https://console.cloud.google.com">
            Google Cloud
          </b-navbar-item>
          <b-navbar-item href="https://admin.google.com/u/1/?pli=1">
            Google Apps
          </b-navbar-item>
        </b-navbar-dropdown>

        <b-navbar-item href="/admin" v-if="admin">
          Admin
        </b-navbar-item>
      </template>

      <template slot="end">
        <div v-if="!authenticated">
          <b-navbar-item tag="div">
            <div class="buttons">
              <router-link class="button is-primary" to="/register">Register</router-link>
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
              <router-link class="button is-primary" to="/logout">Logout</router-link>
            </b-navbar-item>
          </b-navbar-dropdown>
        </div>
      </template>

    </b-navbar>
  </div>
</template>

<script>

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
    }
  },
  mounted () {
    this.checkAuthenticated()
  }
}
</script>

<style scoped>
.navHeight {
  height: 52px;
}

</style>

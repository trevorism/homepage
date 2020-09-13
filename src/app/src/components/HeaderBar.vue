<template>
  <div id="header">
    <section class="hero is-primary">
      <div class="hero-body">
        <h1 class="title">Trevorism</h1>
        <div v-if="!authenticated">
          <router-link style="color: red" to="/login">Login</router-link>
        </div>
        <div v-else>
          <a @click="logout()" style="color: red">Logout</a>
        </div>
      </div>
      <div>
        <router-link style="color: green" to="/admin">Admin</router-link>
      </div>
    </section>
  </div>
</template>

<script>
import auth from '../mixins/auth'

export default {
  name: 'Header',
  mixins: [auth],
  mounted () {
    this.checkAuthenticated()
  }
}
</script>

<style scoped>

</style>

<template>
<div id="login">
  <b-field label="username">
    <b-input v-model="username"></b-input>
  </b-field>
  <b-field label="password">
    <b-input type="password" v-model="password"></b-input>
  </b-field>
  <button class="button is-primary" :disabled="disabled" @click="invokeButton">
    Submit
    <b-loading :is-full-page="false" :active.sync="disabled" :can-cancel="false"></b-loading>
  </button>
</div>
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

</style>

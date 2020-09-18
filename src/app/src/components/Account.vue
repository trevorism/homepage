<template>
  <div id="account">
    <HeaderBar></HeaderBar>
    <section class="marginSpacer">
      <h1 class="title is-4 vertSpacer">Trevorism Profile</h1>

    <div class="card" style="width: 500px">
      <div class="card-content">

        <p class="title">
          <span>{{ user.username }}</span><span v-if="user.admin" class="blueRight">Administrator</span>
        </p>
        <p class="subtitle">
          {{ user.email }}
        </p>
      </div>
      <footer class="card-footer">
        <p class="card-footer-item"><router-link to="/change">Change Password</router-link></p>
      </footer>
      <b-loading :is-full-page="false" :active.sync="loading" :can-cancel="false"></b-loading>
    </div>
    </section>

  </div>
</template>

<script>
import HeaderBar from './HeaderBar'
import axios from 'axios'

export default {
  name: 'Account',
  components: {HeaderBar},
  data () {
    return {
      user: {},
      loading: true
    }
  },
  mounted () {
    axios.get('api/user')
      .then(response => {
        this.user = response.data
        this.loading = false
      })
  }
}
</script>

<style scoped>
  .marginSpacer {
    margin: 20px;
  }

  .vertSpacer {
    margin-top: 60px;
    margin-bottom: 60px;
  }

  .blueRight {
    font-size: 12px;
    float: right;
    color: #0d64c6;
  }
</style>

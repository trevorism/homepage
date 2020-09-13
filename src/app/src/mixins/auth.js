import axios from 'axios'

export default {
  data () {
    return {
      authenticated: false
    }
  },
  methods: {
    checkAuthenticated: function () {
      let session = this.$cookies.get('session')
      this.authenticated = !!session
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
  }
}

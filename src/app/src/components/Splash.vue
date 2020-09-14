<template>
<div id="splash">
  <HeaderBar></HeaderBar>
  Splash content

  <button @click="myButton">Server Request</button>
  <button @click="clear">Clear</button>
  <br/>
  <br/>
  Current STate: {{text}}
</div>
</template>

<script>
import HeaderBar from './HeaderBar'
import axios from 'axios'

export default {
  name: 'Splash',
  components: {HeaderBar},
  data () {
    return {
      'text': ''
    }
  },
  methods: {
    myButton: function () {
      let self = this
      let session = this.$cookies.get('session')
      axios.get('api/secure', { 'headers': { 'Authorization': 'bearer ' + session } })
        .then((val) => {
          self.text = val
        })
        .catch((val) => {
          self.text = val
        })
    },
    clear: function () {
      this.text = ''
    }
  }

}
</script>

<style scoped>

</style>

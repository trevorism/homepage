<template>
  <div id="payment" class="container formWidth">
    <form class="loginBorder">
      <div class="mx-4 mt-4 mb-4">
        <b-field label="Payment Amount (USD)">
          <b-input
            type="number"
            step="0.01"
            min="0.99"
            size="6"
            required
            v-model="amount"
            :autofocus="true">
          </b-input>
        </b-field>

        <div class="is-centered has-text-centered">
          <button class="button is-primary" :disabled="disabled" @click="invokeButton">
            Pay with Stripe: {{amount}}
          </button>
          <b-loading :is-full-page="false" :active.sync="disabled" :can-cancel="false"></b-loading>
        </div>
      </div>
    </form>
    <div v-if="successMessage !== ''" class="has-text-centered">{{successMessage}}
      <router-link class="is-info ml-4" to="/login">Login</router-link>
    </div>
    <div class="has-text-centered has-text-danger">{{errorMessage}}</div>
  </div>
</template>

<script>
import { loadStripe } from '@stripe/stripe-js'
import axios from 'axios'
const stripePromise = loadStripe('pk_live_51HbczGKUPlXay6LPD19T6KKerdVzh6pDRX1rSlDLQALdep9aQVoZ2gv9T17YptAw3Ac1mxBzfsqWoooQdPQtqYno00InsjrrOe')

export default {
  name: 'Stripe',

  data () {
    return {
      amount: 4.99,
      disabled: false,
      errorMessage: '',
      successMessage: ''
    }
  },
  methods: {
    invokeButton: async function () {
      const stripe = await stripePromise
      let request = {
        dollars: this.amount
      }
      this.disabled = true
      axios.post('api/payment/session', request)
        .then((response) => {
          this.disabled = false
          this.errorMessage = ''
          this.successMessage = 'Rerouting to payment provider...'
          stripe.redirectToCheckout({
            sessionId: response.data.id
          })
        })
        .catch(() => {
          this.errorMessage = 'Invalid amount. Please enter an amount over $0.99'
          this.successMessage = ''
          this.disabled = false
        })
    }
  }
}
</script>

<style scoped>
.loginBorder {
  border: #dddddd 1px solid;
  background: #efefef;
}
.formWidth {
  width: 400px
}
</style>

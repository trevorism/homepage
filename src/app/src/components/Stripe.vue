<script setup>
import {ref} from 'vue'
import {loadStripe} from '@stripe/stripe-js'
import axios from 'axios'

const stripePromise = loadStripe('pk_live_51HbczGKUPlXay6LPD19T6KKerdVzh6pDRX1rSlDLQALdep9aQVoZ2gv9T17YptAw3Ac1mxBzfsqWoooQdPQtqYno00InsjrrOe')

const amount = ref(4.99)
const disabled = ref(false)
const errorMessage = ref('')
const successMessage = ref('')

const checkout = async () => {
  const stripe = await stripePromise
  disabled.value = true
  successMessage.value = 'Rerouting to payment provider...'

  const request = {
    dollars: amount.value
  }

  await axios.post('api/payment/session', request).then((response) => {
    disabled.value = false
    errorMessage.value = ''
    successMessage.value = ''
    stripe.redirectToCheckout({
      sessionId: response.data.id
    })
  }).catch(() => {
    errorMessage.value = 'Submission Error. Please enter an amount over $0.99'
    successMessage.value = ''
    disabled.value = false
  });
}
</script>

<template>
  <va-form>
    <span>
      <va-input v-model="amount" label="Funding Amount">
      </va-input>
      <va-button style="margin-top:18px" type="submit" color="primary" @click="checkout" :disabled="disabled">
        <VaInnerLoading :loading="disabled"> Submit </VaInnerLoading>
      </va-button>
      <div v-if="errorMessage.length > 0" class="text-left text-red-600">{{ errorMessage }}</div>
      <div v-if="successMessage.length > 0" class="text-left">{{ successMessage }}</div>
    </span>
  </va-form>

</template>

<style scoped>

</style>
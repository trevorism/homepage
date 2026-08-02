<script setup>
import {ref} from 'vue'
import axios from 'axios'

const amount = ref(4.99)
const disabled = ref(false)
const errorMessage = ref('')
const successMessage = ref('')

const checkout = async () => {
  disabled.value = true
  errorMessage.value = ''
  successMessage.value = 'Rerouting to payment provider...'

  const request = {
    dollars: amount.value
  }

  try {
    const response = await axios.post('api/payment/session', request)
    if (!response.data?.url) {
      throw new Error('No checkout url returned')
    }
    window.location.href = response.data.url
  } catch (error) {
    const status = error.response?.status
    errorMessage.value = status === 400 || status === 500
      ? 'Submission Error. Please enter an amount over $0.99'
      : 'Unable to reach the payment provider. Please try again.'
    successMessage.value = ''
    disabled.value = false
  }
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

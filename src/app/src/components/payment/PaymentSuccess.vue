<template>
  <div id="paymentSuccess">
    <HeaderBar />
    <div class="container mt-6 mb-6">
      <div class="title mb-6">
        Payment Success
      </div>
      <div class="subtitle">
        Thank you for your payment.
      </div>
    </div>
  </div>
</template>

<script>
import HeaderBar from '../HeaderBar'

export default {
  name: 'PaymentSuccess',
  components: {HeaderBar}
}
</script>

<style scoped>

</style>

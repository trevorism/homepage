<template>
  <div id="contact">
    <HeaderBar></HeaderBar>
    <div class="container mt-6 mb-6">
      <div class="title mb-6">
        Contact Us
      </div>
      <div class="subtitle">
        Please contact us at feedback@trevorism.com for pricing or general inquiries.
      </div>
      <div class="subtitle">
        Please contact us at hr@trevorism.com for career information.
      </div>
      <div class="subtitle">
        Funding: help fund Trevorism by making a payment below. We would certainly appreciate it!
        <div class="mt-6"><stripe/></div>
      </div>
    </div>
  </div>
</template>

<script>
import HeaderBar from './HeaderBar'
import Stripe from './payment/Stripe'

export default {
  name: 'Contact',
  components: {Stripe, HeaderBar}
}
</script>

<style scoped>

</style>

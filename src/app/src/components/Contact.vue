<template>
  <div id="contact">
    <HeaderBar></HeaderBar>
    <div class="container mt-6 mb-6">
      <div class="title mb-6">
        Contact Us
      </div>
      <div class="subtitle">
        Please contact us at feedback@trevorism.com for pricing, feature requests, or general inquiries.
      </div>
      <div class="subtitle">
        Please contact us at hr@trevorism.com for career information.
      </div>
      <div class="title mt-6 mb-6">
        Funding
      </div>
      <div class="subtitle">
        Help fund Trevorism by making a payment below. We certainly would appreciate it!
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

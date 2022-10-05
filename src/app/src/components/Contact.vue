<template>
  <div id="contact">
    <HeaderBar></HeaderBar>
    <div class="container mt-6 mb-6">
      <div class="title mb-6">
        Consultant Services
      </div>
      <div class="content">
        Trevorism LLC provides full-stack software consultancy services with a speciality in:
        <ul>
          <li>Distributed web service design and implementation</li>
          <li>DevSecOps mindset -- automating software releases with baked-in security</li>
          <li>Cloud architecture, cloud migrations, and optimizing cloud spend</li>
          <li>Mentoring software teams to achieve increased maturity</li>
        </ul>
      </div>
      <div class="content">
        We are experts at tackling these common problems:
        <ul>
          <li>You are missing deadlines for software delivery and need help fast</li>
          <li>Your organization is going through a cloud transformation and you need a guide with some deep experience</li>
          <li>You want to start building a software system and need a strong foundation</li>
          <li>You need to automate or improve a business process with quantitative results</li>
        </ul>
      </div>
      <div class="title mb-6">
        Contact Us
      </div>
      <div class="subtitle">
        Please contact us at feedback@trevorism.com for pricing, feature requests, or general inquiries.
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

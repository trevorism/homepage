<template>
  <div class="grid justify-items-center" id="contact">
    <HeaderBar :local="true"></HeaderBar>
    <div class="container px-4">
      <h1 class="text-3xl font-bold -ml-4 mb-2">
        Consultant Services
      </h1>
      <div class="mb-2 ml-2">
        Trevorism LLC provides full-stack software consultancy services with a speciality in:
        <ul class="list-disc ml-6">
          <li>Distributed web service design and implementation</li>
          <li>DevSecOps mindset -- automating software releases with baked-in security</li>
          <li>Cloud architecture, cloud migrations, and optimizing cloud spend</li>
          <li>Mentoring software teams to achieve increased maturity</li>
        </ul>
      </div>
      <div class="mb-2 ml-2">
        We are experts at tackling these common problems:
        <ul class="list-disc ml-6">
          <li>You are missing deadlines for software delivery and need help fast</li>
          <li>Your organization is going through a cloud transformation and you need a guide with some deep experience</li>
          <li>You want to start building a software system and need a strong foundation</li>
          <li>You need to automate or improve a business process with quantitative results</li>
        </ul>
      </div>
      <h2 class="text-xl font-bold -ml-4 mb-2">
        Contact Us
      </h2>
      <p class="mb-2">
        Please contact us at feedback@trevorism.com for pricing, feature requests, or general inquiries.
      </p>
    </div>
  </div>
</template>

<script>
import HeaderBar from '@trevorism/ui-header-bar'

export default {
  name: 'Contact',
  components: {HeaderBar}
}
</script>

<style scoped>

</style>

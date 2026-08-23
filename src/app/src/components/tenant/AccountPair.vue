<template>
  <div class="grid md:grid-cols-2 gap-3 my-3">
    <div class="border rounded-md p-3">
      <p class="font-bold">{{ username }} at trevorism.com</p>
      <p class="text-sm mt-1">
        Your existing account. Nothing about it changes &mdash; same password, same sign in at trevorism.com.
      </p>
    </div>
    <div class="border rounded-md p-3">
      <p class="font-bold">{{ username }} at {{ tenantName }}</p>
      <p class="text-sm mt-1">
        A brand new, separate account that happens to share your username. It has its own password, which you set
        from an emailed link, and its own sign in address.
      </p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AccountPair',
  props: {
    username: { type: String, default: 'your username' },
    tenantName: { type: String, default: 'your tenant' }
  }
}
</script>

<style scoped></style>

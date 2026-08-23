<template>
  <div>
    <div v-if="!timedOut">
      <h2 class="text-xl font-bold mb-2">Payment received</h2>
      <va-inner-loading :loading="true" class="py-4">
        <p class="mb-2">Setting up your tenant. This usually takes a few seconds &mdash; please keep this page open.</p>
      </va-inner-loading>
    </div>

    <div v-else>
      <h2 class="text-xl font-bold mb-2">Payment received</h2>
      <p class="mb-2">
        Your payment went through. Setup is taking longer than usual, so we will email you the moment your tenant is
        ready &mdash; you do not need to pay again or stay on this page.
      </p>
      <p class="mb-2 text-sm">
        If you have not heard from us within an hour, get in touch from the
        <router-link to="/contact">contact page</router-link> and quote request {{ requestId }}.
      </p>
      <va-button color="primary" @click="$emit('retry')">Check again</va-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TenantProvisioning',
  props: {
    timedOut: { type: Boolean, default: false },
    requestId: { type: String, default: '' }
  },
  emits: ['retry']
}
</script>

<style scoped></style>

<template>
  <va-alert color="danger" class="mb-3">
    <p>
      <strong>We could not collect this month's payment.</strong>
      {{ tenantName }} keeps running until {{ formattedDeadline }}, then administrator access is suspended.
    </p>
    <va-button color="primary" class="mt-2" :disabled="busy" @click="$emit('manage-billing')">
      <VaInnerLoading :loading="busy"> Update payment method </VaInnerLoading>
    </va-button>
  </va-alert>
</template>

<script>
export default {
  name: 'TenantGraceBanner',
  props: {
    tenantName: { type: String, required: true },
    accessEndsOn: { type: String, required: true },
    busy: { type: Boolean, default: false }
  },
  emits: ['manage-billing'],
  computed: {
    formattedDeadline() {
      const deadline = new Date(this.accessEndsOn)
      if (isNaN(deadline)) {
        return 'soon'
      }
      return deadline.toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' })
    }
  }
}
</script>

<style scoped></style>

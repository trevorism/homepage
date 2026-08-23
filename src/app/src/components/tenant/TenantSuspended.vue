<template>
  <div>
    <p class="mb-2">
      <va-icon name="pause_circle" size="24px" color="warning"></va-icon>
      <strong>{{ request.name }}</strong> is suspended because the subscription is no longer active.
    </p>
    <p class="mb-2">
      Your tenant and its data are retained. Restoring the subscription brings back administrator access and your
      existing sign in address.
    </p>
    <p class="mb-2" v-if="subscriptionSummary">{{ subscriptionSummary }}</p>

    <va-button v-if="subscriptionActive" color="primary" :disabled="busy" @click="$emit('claim')">
      <VaInnerLoading :loading="busy"> Restore my tenant </VaInnerLoading>
    </va-button>
    <va-button v-else color="primary" :disabled="busy" @click="$emit('checkout')">
      <VaInnerLoading :loading="busy"> Restart subscription &mdash; {{ monthlyPrice }} </VaInnerLoading>
    </va-button>
  </div>
</template>

<script>
export default {
  name: 'TenantSuspended',
  props: {
    request: { type: Object, required: true },
    subscriptionSummary: { type: String, default: '' },
    subscriptionActive: { type: Boolean, default: false },
    monthlyPrice: { type: String, required: true },
    busy: { type: Boolean, default: false }
  },
  emits: ['claim', 'checkout']
}
</script>

<style scoped></style>

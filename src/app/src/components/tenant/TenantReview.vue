<template>
  <div>
    <h2 class="text-xl font-bold mb-2">Review before paying</h2>

    <va-list class="mb-3">
      <va-list-item>
        <va-list-item-section>
          <va-list-item-label caption>Tenant name</va-list-item-label>
          <va-list-item-label>{{ name }}</va-list-item-label>
        </va-list-item-section>
      </va-list-item>
      <va-list-item v-if="domain">
        <va-list-item-section>
          <va-list-item-label caption>Tenant domain</va-list-item-label>
          <va-list-item-label>{{ domain }}</va-list-item-label>
        </va-list-item-section>
      </va-list-item>
      <va-list-item>
        <va-list-item-section>
          <va-list-item-label caption>Price</va-list-item-label>
          <va-list-item-label>{{ monthlyPrice }}, recurring until you cancel</va-list-item-label>
        </va-list-item-section>
      </va-list-item>
    </va-list>

    <h3 class="font-bold">What this creates</h3>
    <AccountPair :username="username" :tenant-name="name"></AccountPair>

    <p class="text-sm mb-3">
      Cancel any time from the billing portal on this page. Cancelling suspends tenant administrator access; your data
      is retained.
    </p>

    <va-button color="primary" :disabled="busy" @click="$emit('pay')">
      <VaInnerLoading :loading="busy"> Continue to Stripe &mdash; {{ monthlyPrice }} </VaInnerLoading>
    </va-button>
    <va-button preset="secondary" class="ml-2" :disabled="busy" @click="$emit('back')">Back</va-button>
  </div>
</template>

<script>
import AccountPair from './AccountPair.vue'

export default {
  name: 'TenantReview',
  components: { AccountPair },
  props: {
    name: { type: String, required: true },
    domain: { type: String, default: '' },
    username: { type: String, default: 'your username' },
    monthlyPrice: { type: String, required: true },
    busy: { type: Boolean, default: false }
  },
  emits: ['pay', 'back']
}
</script>

<style scoped></style>

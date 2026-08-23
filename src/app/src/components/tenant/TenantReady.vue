<template>
  <div>
    <p class="mb-2">
      <va-icon name="check_circle" size="24px" color="success"></va-icon>
      <strong>{{ request.name }}</strong> is active.
    </p>

    <div class="border rounded-md p-3 mb-3">
      <h2 class="font-bold mb-2">Sign in to your tenant</h2>
      <p class="mb-1">
        <a :href="request.loginUrl">{{ request.loginUrl }}</a>
      </p>
      <p class="text-sm mb-2">
        Tenant id <code>{{ request.tenantGuid }}</code>
        <va-button preset="secondary" size="small" class="ml-2" @click="copyTenantId">
          {{ copied ? 'Copied' : 'Copy' }}
        </va-button>
      </p>
      <p class="text-sm">
        Sign in as <strong>{{ username }}</strong> using the password you set from the email we just sent.
      </p>
    </div>

    <va-alert color="warning" class="mb-3">
      <p>
        <strong>Set your password within {{ passwordDeadlineHours }} hours.</strong> The temporary password in your
        email expires after that.
      </p>
      <p class="text-sm mt-1">
        Missed it, or never got the email? Request a new one at
        <a :href="forgotPasswordUrl">{{ forgotPasswordUrl }}</a>
      </p>
    </va-alert>

    <h2 class="font-bold">Your two accounts</h2>
    <AccountPair :username="username" :tenant-name="request.name"></AccountPair>

    <h2 class="font-bold mb-2">What to do next</h2>
    <ul class="list-disc ml-6 mb-3">
      <li>Set your tenant password from the emailed link.</li>
      <li>
        Invite and manage your users in the
        <a :href="adminConsoleUrl">admin console</a>.
      </li>
      <li>Bookmark your tenant sign in address above.</li>
    </ul>

    <p class="mb-2" v-if="subscriptionSummary">{{ subscriptionSummary }}</p>
    <va-button color="primary" :disabled="busy" @click="$emit('manage-billing')">
      <VaInnerLoading :loading="busy"> Manage billing </VaInnerLoading>
    </va-button>
    <p class="text-sm mt-2">
      Update your card, view invoices, or cancel. Cancelling suspends tenant administrator access; your data is
      retained.
    </p>
  </div>
</template>

<script>
import AccountPair from './AccountPair.vue'

export default {
  name: 'TenantReady',
  components: { AccountPair },
  props: {
    request: { type: Object, required: true },
    username: { type: String, default: 'your username' },
    subscriptionSummary: { type: String, default: '' },
    passwordDeadlineHours: { type: Number, default: 24 },
    adminConsoleUrl: { type: String, default: 'https://admin.auth.trevorism.com' },
    busy: { type: Boolean, default: false }
  },
  emits: ['manage-billing'],
  data() {
    return {
      copied: false
    }
  },
  computed: {
    forgotPasswordUrl() {
      return `https://login.auth.trevorism.com/forgot/${this.request.tenantGuid}`
    }
  },
  methods: {
    async copyTenantId() {
      try {
        await navigator.clipboard.writeText(this.request.tenantGuid)
        this.copied = true
      } catch {
        this.copied = false
      }
    }
  }
}
</script>

<style scoped></style>

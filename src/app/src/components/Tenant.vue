<template>
  <div class="grid justify-items-center" id="tenant">
    <HeaderBar :local="true"></HeaderBar>
    <div class="container px-4">
      <h1 class="text-2xl font-bold mb-2">Your Trevorism Tenant</h1>

      <va-inner-loading :loading="loading">
        <div v-if="isProvisioned">
          <p class="mb-2">
            <va-icon name="check_circle" size="24px" color="success"></va-icon>
            <strong>{{ request.name }}</strong> is active at {{ request.domain }}.
          </p>
          <p class="mb-2">
            You are the tenant administrator. Sign in at
            <a :href="'https://' + request.domain">{{ request.domain }}</a> with your Trevorism username.
          </p>
          <p class="text-sm">{{ cancellationNotice }}</p>
        </div>

        <div v-else-if="isSuspended">
          <p class="mb-2">
            <va-icon name="pause_circle" size="24px" color="warning"></va-icon>
            <strong>{{ request.name }}</strong> is suspended because the subscription is no longer active.
          </p>
          <va-button color="primary" @click="startCheckout" :disabled="busy">
            <VaInnerLoading :loading="busy"> Restart subscription </VaInnerLoading>
          </va-button>
        </div>

        <div v-else-if="isAwaitingPayment">
          <p class="mb-2">
            <strong>{{ request.name }}</strong> ({{ request.domain }}) is reserved and waiting for payment.
          </p>
          <va-button color="primary" @click="startCheckout" :disabled="busy">
            <VaInnerLoading :loading="busy"> Continue to payment &mdash; {{ monthlyPrice }} </VaInnerLoading>
          </va-button>
        </div>

        <div v-else>
          <p class="mb-2">
            Create your own tenant for {{ monthlyPrice }}. You become its administrator and can invite your own users.
          </p>
          <va-form>
            <va-input v-model="name" label="Tenant Name" class="mb-2"></va-input>
            <va-input v-model="domain" label="Tenant Domain" placeholder="example.com" class="mb-2"></va-input>
            <va-button style="margin-top: 18px" type="submit" color="primary" @click="createRequest" :disabled="busy">
              <VaInnerLoading :loading="busy"> Create tenant &mdash; {{ monthlyPrice }} </VaInnerLoading>
            </va-button>
          </va-form>
        </div>

        <div v-if="errorMessage.length > 0" class="text-left text-red-600 mt-2">{{ errorMessage }}</div>
        <div v-if="successMessage.length > 0" class="text-left mt-2">{{ successMessage }}</div>
      </va-inner-loading>
    </div>
  </div>
</template>

<script>
import HeaderBar from '@trevorism/ui-header-bar'
import axios from 'axios'

export default {
  name: 'Tenant',
  components: { HeaderBar },
  data() {
    return {
      monthlyPrice: '$10.00 / month',
      cancellationNotice: 'Cancel anytime from your billing provider. Cancelling suspends tenant administrator access.',
      name: '',
      domain: '',
      request: null,
      busy: false,
      loading: true,
      errorMessage: '',
      successMessage: ''
    }
  },
  computed: {
    isProvisioned() {
      return this.request?.status === 'PROVISIONED'
    },
    isSuspended() {
      return this.request?.status === 'SUSPENDED'
    },
    isAwaitingPayment() {
      return this.request?.status === 'PENDING_PAYMENT'
    }
  },
  async mounted() {
    await this.loadRequest()

    const params = new URLSearchParams(window.location.search)
    const returnedRequestId = params.get('request')
    if (params.get('status') === 'success' && returnedRequestId) {
      await this.provision(returnedRequestId)
    }
  },
  methods: {
    describeError(error, fallback) {
      return error.response?.data?.message || error.response?.data?._embedded?.errors?.[0]?.message || fallback
    },
    async loadRequest() {
      try {
        const response = await axios.get('api/subscribedtenant')
        this.request = response.data && response.data.id ? response.data : null
      } catch {
        this.request = null
      } finally {
        this.loading = false
      }
    },
    async createRequest() {
      this.busy = true
      this.errorMessage = ''
      this.successMessage = ''

      try {
        const response = await axios.post('api/subscribedtenant', { name: this.name, domain: this.domain })
        this.request = response.data
        await this.startCheckout()
      } catch (error) {
        this.errorMessage = this.describeError(error, 'Unable to create the tenant request.')
        this.busy = false
      }
    },
    async startCheckout() {
      this.busy = true
      this.errorMessage = ''
      this.successMessage = 'Rerouting to payment provider...'

      try {
        const response = await axios.post(`api/subscribedtenant/${this.request.id}/session`)
        if (!response.data?.url) {
          throw new Error('No checkout url returned')
        }
        window.location.href = response.data.url
      } catch (error) {
        this.errorMessage = this.describeError(error, 'Unable to reach the payment provider. Please try again.')
        this.successMessage = ''
        this.busy = false
      }
    },
    async provision(requestId) {
      this.busy = true
      this.errorMessage = ''
      this.successMessage = 'Setting up your tenant...'

      try {
        const response = await axios.post(`api/subscribedtenant/${requestId}/provision`)
        this.request = response.data
        this.successMessage = 'Your tenant is ready. Check your email for a temporary administrator password.'
      } catch (error) {
        this.errorMessage = this.describeError(error, 'We could not confirm your subscription yet. Please try again shortly.')
        this.successMessage = ''
      } finally {
        this.busy = false
      }
    }
  }
}
</script>

<style scoped></style>

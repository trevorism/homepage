<template>
  <div class="grid justify-items-center" id="tenant">
    <HeaderBar :local="true"></HeaderBar>
    <div class="container px-4">
      <h1 class="text-2xl font-bold mb-2">Your Trevorism Tenant</h1>

      <va-inner-loading :loading="loading">
        <va-stepper v-if="showSteps" :model-value="stepIndex" :steps="steps" disabled class="mb-4"></va-stepper>

        <TenantGraceBanner
          v-if="isInGracePeriod"
          :tenant-name="request.name"
          :access-ends-on="request.accessEndsOn"
          :busy="busy"
          @manage-billing="openBillingPortal"
        ></TenantGraceBanner>

        <p v-if="stage === 'cancelled'" class="mb-3">
          Payment cancelled &mdash; you have not been charged. <strong>{{ request.name }}</strong> and
          {{ request.domain }} are still reserved for you, so you can pick up where you left off whenever you are ready.
        </p>

        <TenantReady
          v-if="stage === 'ready'"
          :request="request"
          :username="username"
          :subscription-summary="subscriptionSummary"
          :busy="busy"
          @manage-billing="openBillingPortal"
        ></TenantReady>

        <TenantSuspended
          v-else-if="stage === 'suspended'"
          :request="request"
          :subscription-summary="subscriptionSummary"
          :subscription-active="subscriptionActive"
          :monthly-price="monthlyPrice"
          :busy="busy"
          @claim="claimNow"
          @checkout="startCheckout"
        ></TenantSuspended>

        <TenantProvisioning
          v-else-if="stage === 'finalizing'"
          :timed-out="provisioningTimedOut"
          :request-id="request?.id || ''"
          @retry="resumeProvisioning"
        ></TenantProvisioning>

        <TenantCreateForm
          v-else-if="stage === 'details'"
          :name="draft.name"
          :domain="draft.domain"
          :check-availability="checkAvailability"
          @continue="reviewDraft"
        ></TenantCreateForm>

        <TenantReview
          v-else-if="stage === 'review'"
          :name="draft.name"
          :domain="draft.domain"
          :username="username"
          :monthly-price="monthlyPrice"
          :busy="busy"
          @pay="createRequestAndPay"
          @back="stage = 'details'"
        ></TenantReview>

        <div v-else-if="stage === 'pending' || stage === 'cancelled'">
          <p class="mb-2">
            <strong>{{ request.name }}</strong> ({{ request.domain }}) is reserved and waiting for payment.
          </p>
          <p class="mb-2" v-if="subscriptionSummary">{{ subscriptionSummary }}</p>
          <va-button v-if="subscriptionActive" color="primary" :disabled="busy" @click="claimNow">
            <VaInnerLoading :loading="busy"> Finish setting up my tenant </VaInnerLoading>
          </va-button>
          <va-button v-else color="primary" :disabled="busy" @click="startCheckout">
            <VaInnerLoading :loading="busy"> Continue to payment &mdash; {{ monthlyPrice }} </VaInnerLoading>
          </va-button>
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
import { useCookies } from 'vue3-cookies'
import TenantCreateForm from './tenant/TenantCreateForm.vue'
import TenantReview from './tenant/TenantReview.vue'
import TenantProvisioning from './tenant/TenantProvisioning.vue'
import TenantReady from './tenant/TenantReady.vue'
import TenantSuspended from './tenant/TenantSuspended.vue'
import TenantGraceBanner from './tenant/TenantGraceBanner.vue'

const DEFAULT_POLL_DELAYS = [3000, 3000, 5000, 5000, 10000, 10000, 15000, 15000, 20000]

export default {
  name: 'Tenant',
  components: {
    HeaderBar,
    TenantCreateForm,
    TenantReview,
    TenantProvisioning,
    TenantReady,
    TenantSuspended,
    TenantGraceBanner
  },
  props: {
    pollDelays: { type: Array, default: () => DEFAULT_POLL_DELAYS }
  },
  data() {
    return {
      monthlyPrice: '$10.00 / month',
      steps: [{ label: 'Details' }, { label: 'Review' }, { label: 'Payment' }, { label: 'Ready' }],
      stage: 'details',
      draft: { name: '', domain: '' },
      request: null,
      subscription: null,
      username: '',
      busy: false,
      loading: true,
      provisioningTimedOut: false,
      errorMessage: '',
      successMessage: ''
    }
  },
  computed: {
    showSteps() {
      return ['details', 'review', 'finalizing', 'ready'].includes(this.stage)
    },
    stepIndex() {
      return { details: 0, review: 1, finalizing: 2, ready: 3 }[this.stage] ?? 0
    },
    isInGracePeriod() {
      return this.stage === 'ready' && Boolean(this.request?.accessEndsOn)
    },
    renewalDate() {
      const paidThrough = this.subscription?.paidThrough || this.request?.paidThrough
      if (!paidThrough) {
        return ''
      }
      const renewal = new Date(paidThrough)
      if (isNaN(renewal)) {
        return ''
      }
      return renewal.toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' })
    },
    subscriptionActive() {
      return this.subscription?.state === 'ACTIVE'
    },
    subscriptionSummary() {
      if (this.subscriptionActive) {
        return this.renewalDate
          ? `Your subscription is active and renews on ${this.renewalDate}.`
          : 'Your subscription is active.'
      }
      if (this.subscription?.state === 'INACTIVE') {
        return 'We could not find an active subscription for your account.'
      }
      return ''
    }
  },
  async mounted() {
    const { cookies } = useCookies()
    this.username = cookies.get('user_name') || 'your username'

    await Promise.all([this.loadRequest(), this.loadSubscription()])

    const params = new URLSearchParams(window.location.search)
    const returnedRequestId = params.get('request')
    const returnedStatus = params.get('status')

    if (returnedStatus === 'success' && returnedRequestId) {
      await this.pollForProvisioning(returnedRequestId)
      return
    }

    if (returnedStatus === 'cancelled' && this.request) {
      this.stage = 'cancelled'
      return
    }

    await this.claimTenantIfAlreadyPaid()
  },
  methods: {
    describeError(error, fallback) {
      return error.response?.data?.message || error.response?.data?._embedded?.errors?.[0]?.message || fallback
    },
    stageForRequest() {
      if (!this.request) {
        return 'details'
      }
      if (this.request.status === 'PROVISIONED') {
        return 'ready'
      }
      if (this.request.status === 'SUSPENDED') {
        return 'suspended'
      }
      return 'pending'
    },
    async loadRequest() {
      try {
        const response = await axios.get('api/subscribedtenant')
        this.request = response.data && response.data.id ? response.data : null
      } catch {
        this.request = null
      } finally {
        this.stage = this.stageForRequest()
        this.loading = false
      }
    },
    async loadSubscription() {
      const response = await axios.get('api/subscribedtenant/subscription').catch(() => null)
      this.subscription = response ? response.data : null
    },
    async checkAvailability(candidate) {
      const response = await axios.post('api/subscribedtenant/availability', candidate).catch(() => null)
      return response ? response.data : { available: false, message: 'Unable to check availability right now.' }
    },
    reviewDraft(candidate) {
      this.draft = candidate
      this.errorMessage = ''
      this.stage = 'review'
    },
    async claimTenantIfAlreadyPaid() {
      if (this.stage !== 'pending' || !this.subscriptionActive) {
        return
      }
      await this.claimNow()
    },
    async claimNow() {
      this.busy = true
      this.errorMessage = ''

      try {
        const response = await axios.post(`api/subscribedtenant/${this.request.id}/tenant`)
        this.request = response.data
        this.stage = this.stageForRequest()
      } catch (error) {
        this.errorMessage = this.describeError(error, 'We could not set up your tenant yet. Please try again shortly.')
      } finally {
        this.busy = false
      }
    },
    async createRequestAndPay() {
      this.busy = true
      this.errorMessage = ''
      this.successMessage = ''

      try {
        const response = await axios.post('api/subscribedtenant', this.draft)
        this.request = response.data
        await this.startCheckout()
      } catch (error) {
        this.errorMessage = this.describeError(error, 'Unable to create the tenant request.')
        this.stage = 'details'
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
    async openBillingPortal() {
      this.busy = true
      this.errorMessage = ''

      try {
        const response = await axios.post('api/subscribedtenant/portal')
        if (!response.data?.url) {
          throw new Error('No portal url returned')
        }
        window.location.href = response.data.url
      } catch (error) {
        this.errorMessage = this.describeError(error, 'Unable to reach the billing provider. Please try again.')
        this.busy = false
      }
    },
    resumeProvisioning() {
      const requestId = this.request?.id
      if (requestId) {
        this.pollForProvisioning(requestId)
      }
    },
    wait(millis) {
      if (millis <= 0) {
        return Promise.resolve()
      }
      return new Promise((resolve) => setTimeout(resolve, millis))
    },
    async pollForProvisioning(requestId) {
      this.stage = 'finalizing'
      this.provisioningTimedOut = false
      this.errorMessage = ''
      this.successMessage = ''

      for (let attempt = 0; attempt <= this.pollDelays.length; attempt++) {
        if (attempt > 0) {
          await this.wait(this.pollDelays[attempt - 1])
        }

        const response = await axios.post(`api/subscribedtenant/${requestId}/tenant`).catch(() => null)
        if (response?.data?.id) {
          this.request = response.data
        }
        if (response?.data?.status === 'PROVISIONED') {
          this.stage = 'ready'
          return
        }
      }

      this.provisioningTimedOut = true
    }
  }
}
</script>

<style scoped></style>

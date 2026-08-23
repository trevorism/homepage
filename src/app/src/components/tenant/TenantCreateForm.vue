<template>
  <div>
    <p class="mb-3">
      A tenant is your own isolated slice of Trevorism. You become its administrator and can invite your own users.
    </p>

    <va-form>
      <va-input v-model="draftName" label="Tenant Name" class="mb-1"></va-input>
      <p class="text-sm mb-3">Between 3 and 64 characters. Shown to your users.</p>

      <va-input v-model="draftDomain" label="Tenant Domain (optional)" placeholder="example.com" class="mb-1"></va-input>
      <p class="text-sm">
        Optional. If you claim one, it is your tenant's identifier, not a website. We do not host it or configure DNS
        for it, and it cannot be changed later.
      </p>

      <p v-if="checking" class="text-sm mt-2">Checking availability&hellip;</p>
      <p v-else-if="unavailableReason" class="text-sm mt-2 text-red-600">{{ unavailableReason }}</p>
      <p v-else-if="available" class="text-sm mt-2 text-green-700">{{ availabilityMessage }}</p>

      <va-button
        style="margin-top: 18px"
        type="submit"
        color="primary"
        :disabled="!canContinue"
        @click="$emit('continue', { name: draftName, domain: draftDomain })"
      >
        Continue
      </va-button>
    </va-form>
  </div>
</template>

<script>
export default {
  name: 'TenantCreateForm',
  props: {
    name: { type: String, default: '' },
    domain: { type: String, default: '' },
    checkAvailability: { type: Function, required: true },
    debounceMillis: { type: Number, default: 400 }
  },
  emits: ['continue'],
  data() {
    return {
      draftName: this.name,
      draftDomain: this.domain,
      checking: false,
      available: false,
      unavailableReason: '',
      debounceHandle: null
    }
  },
  computed: {
    canContinue() {
      return this.available && !this.checking
    },
    availabilityMessage() {
      return this.draftDomain
        ? `${this.draftName} and ${this.draftDomain} are available.`
        : `${this.draftName} is available.`
    }
  },
  watch: {
    draftName() {
      this.scheduleCheck()
    },
    draftDomain() {
      this.scheduleCheck()
    }
  },
  beforeUnmount() {
    clearTimeout(this.debounceHandle)
  },
  methods: {
    scheduleCheck() {
      this.available = false
      this.unavailableReason = ''
      clearTimeout(this.debounceHandle)

      if (!this.draftName) {
        this.checking = false
        return
      }

      this.checking = true
      this.debounceHandle = setTimeout(() => this.runCheck(), this.debounceMillis)
    },
    async runCheck() {
      const name = this.draftName
      const domain = this.draftDomain
      const result = await this.checkAvailability({ name, domain })

      if (name !== this.draftName || domain !== this.draftDomain) {
        return
      }

      this.checking = false
      this.available = Boolean(result?.available)
      this.unavailableReason = result?.available ? '' : result?.message || ''
    }
  }
}
</script>

<style scoped></style>

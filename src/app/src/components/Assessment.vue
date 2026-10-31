<template>
  <div id="assessment">
    <HeaderBar :local="true"></HeaderBar>
    <div class="container mx-auto max-w-2xl px-6 py-10">
      <h1 class="text-3xl font-bold mb-2">Get a free assessment</h1>
      <p class="mb-8">Takes about five minutes. We reply within a week with a written plan.</p>

      <p v-if="submitted" class="assessment-thanks rounded-md bg-green-50 p-6 text-lg">
        Thanks. We'll read this personally and reply within a week.
      </p>

      <form v-else class="flex flex-col gap-6" novalidate @submit.prevent="submit">
        <va-input v-model="form.name" label="Your name" maxlength="100" />
        <va-input v-model="form.email" type="email" label="Email" maxlength="254" />
        <va-textarea v-model="form.business" label="Business name and what it does" maxlength="500" autosize />
        <va-select v-model="form.teamSize" label="Team size" :options="teamSizes" text-by="label" value-by="value" />
        <va-textarea v-model="form.tools" label="Tools you pay for today" maxlength="2000" autosize />
        <va-textarea v-model="form.task" label="The one task you'd most like to stop doing by hand" maxlength="2000" autosize />
        <va-select v-model="form.hosting" label="Where you'd like it to run" :options="hostingPreferences" text-by="label" value-by="value" />
        <div class="honeypot" aria-hidden="true">
          <label for="website">Website</label>
          <input id="website" v-model="form.website" type="text" name="website" tabindex="-1" autocomplete="off" />
        </div>
        <va-alert v-if="errorMessage" color="danger">{{ errorMessage }}</va-alert>
        <div>
          <va-button color="success" :disabled="submitting" @click="submit">Get my free assessment</va-button>
          <p class="mt-3 text-sm text-slate-500">
            By submitting, you agree to our <router-link to="/privacy" class="underline">privacy policy</router-link>.
          </p>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import axios from 'axios'
import HeaderBar from '@trevorism/ui-header-bar'

const teamSizes = [
  { value: '1-5', label: '1 to 5' },
  { value: '6-20', label: '6 to 20' },
  { value: '21-50', label: '21 to 50' },
  { value: '50+', label: '50+' }
]

const hostingPreferences = [
  { value: 'hosted', label: 'Hosted by us' },
  { value: 'own-account', label: 'In my own Google Cloud account' },
  { value: 'not-sure', label: 'Not sure' }
]

const MISSING_FIELDS_MESSAGE = "Please add your name, a valid email, your business and the task you'd like to stop doing by hand."
const RESPONSE_MESSAGES = {
  400: MISSING_FIELDS_MESSAGE,
  429: 'Too many requests from your network. Please try again in an hour.'
}
const FALLBACK_MESSAGE = 'Something went wrong. Please email feedback@trevorism.com instead.'
const EMAIL_PATTERN = /^[^\s@]+@[^\s@]+\.[^\s@]+$/

const form = reactive({ name: '', email: '', business: '', teamSize: '', tools: '', task: '', hosting: '', website: '' })
const submitting = ref(false)
const submitted = ref(false)
const errorMessage = ref('')

function isComplete() {
  return [form.name, form.business, form.task].every((value) => value.trim().length > 0) && EMAIL_PATTERN.test(form.email.trim())
}

async function submit() {
  if (!isComplete()) {
    errorMessage.value = MISSING_FIELDS_MESSAGE
    return
  }
  submitting.value = true
  errorMessage.value = ''
  try {
    await axios.post('/api/assessment', { ...form })
    submitted.value = true
  } catch (error) {
    errorMessage.value = RESPONSE_MESSAGES[error.response?.status] ?? FALLBACK_MESSAGE
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
.honeypot {
  position: absolute;
  left: -10000px;
  width: 1px;
  height: 1px;
  overflow: hidden;
}
</style>

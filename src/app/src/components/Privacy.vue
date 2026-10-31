<template>
  <div id="privacy">
    <HeaderBar :local="true"></HeaderBar>
    <div class="container mx-auto max-w-3xl px-6 py-10">
      <h1 class="text-3xl font-bold mb-2">Privacy policy</h1>
      <p class="text-sm text-slate-500 mb-8">Last updated October 31, 2026</p>

      <p class="mb-6">
        Trevorism LLC ("we") runs www.trevorism.com. This policy explains what we collect, why, and the choices you
        have. Questions go to <a href="mailto:feedback@trevorism.com" class="underline">feedback@trevorism.com</a>.
      </p>

      <h2 class="text-xl font-bold mb-2">What we collect</h2>
      <ul class="list-disc ml-6 mb-6">
        <li class="mb-1">
          <strong>Assessment requests.</strong> When you request a free assessment, we collect what you enter: your
          name, email address, business name and description, team size, the tools you use, the task you'd like to stop
          doing by hand, and where you'd like your software to run.
        </li>
        <li class="mb-1">
          <strong>Accounts.</strong> If you register, we collect the account details you provide, such as your
          username and email address.
        </li>
        <li class="mb-1">
          <strong>Cookies.</strong> We use a cookie to keep you signed in. We don't use advertising or tracking cookies.
        </li>
        <li class="mb-1">
          <strong>Server logs.</strong> Like most websites, our servers record technical details of each request, such
          as your IP address, browser type and the page requested.
        </li>
      </ul>

      <h2 class="text-xl font-bold mb-2">Why we use it</h2>
      <ul class="list-disc ml-6 mb-6">
        <li class="mb-1">To reply to your assessment request and follow up about it.</li>
        <li class="mb-1">To run your account and sign you in.</li>
        <li class="mb-1">To keep the site secure, prevent abuse and fix problems.</li>
      </ul>
      <p class="mb-6">We don't sell your information, and we don't use it for advertising.</p>

      <h2 class="text-xl font-bold mb-2">Who processes it for us</h2>
      <ul class="list-disc ml-6 mb-6">
        <li class="mb-1"><strong>Google Cloud</strong> hosts the site and stores its data and logs.</li>
        <li class="mb-1"><strong>Mailgun</strong> delivers the emails we send.</li>
      </ul>
      <p class="mb-6">Otherwise we share your information only when the law requires it.</p>

      <h2 class="text-xl font-bold mb-2">How long we keep it</h2>
      <ul class="list-disc ml-6 mb-6">
        <li class="mb-1">Assessment requests: up to two years after we last hear from you, unless you ask us to delete them sooner.</li>
        <li class="mb-1">Account details: for as long as your account exists.</li>
        <li class="mb-1">Server logs: for a limited period, typically 30 days.</li>
      </ul>

      <h2 class="text-xl font-bold mb-2">Your choices</h2>
      <p class="mb-6">
        Email <a href="mailto:feedback@trevorism.com" class="underline">feedback@trevorism.com</a> to see the
        information we hold about you, correct it, or have it deleted. We respond within 30 days.
      </p>

      <h2 class="text-xl font-bold mb-2">Children</h2>
      <p class="mb-6">This site isn't directed to children under 13, and we don't knowingly collect their information.</p>

      <h2 class="text-xl font-bold mb-2">Changes</h2>
      <p class="mb-6">If we change this policy, we'll update the date at the top of this page.</p>
    </div>
  </div>
</template>

<script setup>
import HeaderBar from '@trevorism/ui-header-bar'
</script>

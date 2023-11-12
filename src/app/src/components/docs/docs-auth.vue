<template>
  <div class="grid justify-items-center" id="docs">
    <div class="container px-4">
      <h1 class="text-3xl font-bold -ml-4 mb-2">
        Auth
      </h1>
      <h2 class="text-xl font-bold -ml-4 mb-2">
        Motivation
      </h2>
      <div class="mb-2 ml-2">
        In order to securely access resources in Trevorism, authentication and authorization must be performed. The Trevorism Auth service
        allows for registration of users and applications, and issues signed JWT tokens based on those identities. All services can validate
        incoming tokens and read the included claims to determine if the request is authorized.
      </div>
      <h2 class="text-xl font-bold -ml-4 mb-2">
        API
      </h2>
      <div class="mb-2 ml-2">
        <p class="mb-2">The service is deployed to <a class="text-blue-600 underline dark:text-blue-500 hover:no-underline" href="https://auth.trevorism.com">https://auth.trevorism.com</a>.</p>
        <p class="mb-2">The interface documentation exists at <a class="text-blue-600 underline dark:text-blue-500 hover:no-underline" href="https://auth.trevorism.com/help">https://auth.trevorism.com/help</a>.</p>
        <p class="mb-2">The code is available at <a class="text-blue-600 underline dark:text-blue-500 hover:no-underline" href="https://github.com/trevorism/auth-provider">https://github.com/trevorism/auth-provider</a>.</p>
      </div>
      <h2 class="text-xl font-bold -ml-4 mb-2">
        Usage
      </h2>
      <div class="mb-2 ml-2">
        <p class="mb-2">
          In order to use the auth service, one must register an identity with the service.
          Identities may be either users or applications. Each identity has credentials; either a username and password or a clientId and clientSecret.
        </p>
        <p class="mb-2">
          With valid credentials, a JWT token may be requested. Tokens are short-lived and encrypted such that only Trevorism services may read the tokens.
          Tokens are sent in the Authorization header of each request to a Trevorism service. For example: <span class="font-mono bg-slate-100">{"Authorization": "bearer &lt;token&gt;"}</span>
        </p>
        <p class="mb-2">
          A service securing endpoints may do so by simply annotating the API method with a <span class="text-amber-400"> @Secure</span> annotation.
          The code for implementation of security for each service exists in libraries that are included in each service.
        </p>
        <p class="mb-2">The security utilities exist in <a class="text-blue-600 underline dark:text-blue-500 hover:no-underline" href="https://github.com/trevorism/secure-utils">https://github.com/trevorism/secure-utils</a>.</p>
        <p class="mb-2">The validation code exists in  <a class="text-blue-600 underline dark:text-blue-500 hover:no-underline" href="https://github.com/trevorism/micronaut-security-utils">https://github.com/trevorism/micronaut-security-utils</a>.</p>
      </div>
      <h2 class="text-xl font-bold -ml-4 mb-2">
        Result
      </h2>
      <div class="mb-2 ml-2">
        The Auth service and associated libraries allow for a elegant and secure implementation of authentication and authorization.
      </div>
    </div>

  </div>
</template>

<script>

export default {
  name: 'DocsAuth',
}
</script>

<style scoped>

</style>

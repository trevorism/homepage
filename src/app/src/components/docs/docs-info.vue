<template>
  <div class="grid justify-items-center" id="docs">
    <div class="container px-4">
      <h2 class="text-xl font-bold -ml-4 mb-2">
        This Documentation
      </h2>
      <div class="mb-2 ml-2">
        The following pages of the documentation describe the purpose and usage of each Trevorism service.
      </div>
      <div class="mb-2 ml-2">
        The ecosystem is continuously growing and may not be fully documented. View the full Trevorism service registry <a class="text-blue-600 underline dark:text-blue-500 hover:no-underline"  href="https://active.project.trevorism.com/">here</a>.
      </div>
      <div class="mb-2 ml-2">
        Each of the following entries describes the motivation of the service, the exposed API, and links to the code base and deployed reference implementation.
        One can use the service (for free!) by registering as a user of Trevorism.
      </div>
      <div class="mb-2 ml-2">
        Feel free to <a class="text-blue-600 underline dark:text-blue-500 hover:no-underline" href="https://trevorism.com/contact">contact us</a> for more infomation, questions, or feedback.
      </div>
      <h2 class="text-xl font-bold -ml-4 mb-2">
        Trevorism.com Tech Stack
      </h2>
      <div>
        <div class="mb-2 ml-2">The live reference implementation (this site) has chosen the following technologies:</div>
        <ul class="list-disc ml-6">
          <li>Cloud Platform: Google Cloud Platform -- GCP was chosen for generous free quotas for many services, enabling Trevorism to run at very low cost.</li>
          <li>Code Repository and CI/CD: Github and Github actions -- Github enables open source and provides free containerized compute for builds and tests. </li>
          <li>Backend: Groovy on Micronaut -- Groovy enables rapid development and Micronaut is a elegant web framework which avoids reflection.</li>
          <li>Frontend: Vue and Vuestic -- Vue is a progressive lightweight js framework, and the Vuestic component library eliminates most custom UI work.</li>
          <li>Command Line Interface: Powershell -- Powershell's object pipeline and native web requests allow one to manage the system efficiently</li>
        </ul>
      </div>
    </div>

  </div>
</template>

<script>

export default {
  name: 'DocsInfo',
}
</script>

<style scoped>

</style>

<template>
  <div class="grid justify-items-center" id="docs">
    <div class="container px-4">
      <h1 class="text-3xl font-bold text-center">
        Data
      </h1>
      <h2 class="text-xl font-bold -ml-4 mb-2">
        Motivation
      </h2>
      <div class="mb-2 ml-2">
        An essential part of any web service is management of data. Trevorism provides a REST API for data operations which is agnostic to the underlying data store.
        Since different data stores make different decisions how to access, retrieve and manage data, the top level data API strikes a balance between flexibility and simplicity.
        Datastore specific APIs exists within Trevorism which may fully or partially implement the Data API. Clients of the Data API may opt for
        maximum flexibility by using the top-level interface, or a more optimized experience by using a datastore specific API.
      </div>
      <h2 class="text-xl font-bold -ml-4 mb-2">
        API
      </h2>
      <div class="mb-2 ml-2">
        <p class="mb-2">The service is deployed to <a class="text-blue-600 underline dark:text-blue-500 hover:no-underline" href="https://data.trevorism.com">https://data.trevorism.com</a>.</p>
        <p class="mb-2">The interface documentation exists at <a class="text-blue-600 underline dark:text-blue-500 hover:no-underline" href="https://data.trevorism.com/help">https://data.trevorism.com/help</a>.</p>
        <p class="mb-2">The code is available at <a class="text-blue-600 underline dark:text-blue-500 hover:no-underline" href="https://github.com/trevorism/data">https://github.com/trevorism/data</a>.</p>
      </div>
      <h2 class="text-xl font-bold -ml-4 mb-2">
        Usage
      </h2>
      <div class="mb-2 ml-2">
        <p class="mb-2">
          The Data API has the concept of named entities. Entities are weakly typed; the only requirement is that a given entity type must have a unique id.
          The API allows for CRUD and query operations on these entities.
        </p>
        <p class="mb-2">
          Data may exist in multiple data stores. The Data API utilizes a data catalog service, deployed at <a class="text-blue-600 underline dark:text-blue-500 hover:no-underline" href="https://catalog.data.trevorism.com">https://catalog.data.trevorism.com</a> to map entity names to data stores.
          A data locator in the format <span class="font-mono bg-slate-100">&lt;datastore&gt;:&lt;entity name&gt;:&lt;entity id&gt;</span> is used to retrieve objects irrespective of their physical location.
          Further, one can perform cross data store transfers, joins and other multi-data-store operations.
        </p>
      </div>
      <h2 class="text-xl font-bold -ml-4 mb-2">
        Result
      </h2>
      <div class="mb-2 ml-2">
        By exposing data as a service, any Trevorism service inherits the ability to seemlessly manage its data. For generic use cases, out-of-the-box data
        management dramatically increases development speed since service need not implement their own secure data access layer.
      </div>
      <div class="mb-2 ml-2">
        For more complex use cases, one can create data store specific APIs that are Trevorism Data API compliant. This allows for enterprise wide management of data
        and enables interactions across data stores via a simple RESTful API.
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

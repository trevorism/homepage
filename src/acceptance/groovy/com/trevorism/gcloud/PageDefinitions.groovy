package com.trevorism.gcloud

this.metaClass.mixin(io.cucumber.groovy.Hooks)
this.metaClass.mixin(io.cucumber.groovy.EN)

String baseUrl = System.getenv("ACCEPTANCE_BASE_URL") ?: "https://www.trevorism.com"
String crawlerUserAgent = "Mozilla/5.0 (compatible; ClaudeBot/1.0)"

int responseStatus
String responseBody

When(~/^a crawler requests "([^"]*)"$/) { String path ->
    HttpURLConnection connection = new URL("${baseUrl}${path}").openConnection() as HttpURLConnection
    connection.setRequestProperty("User-Agent", crawlerUserAgent)
    connection.setRequestProperty("Accept", "*/*")
    responseStatus = connection.responseCode
    InputStream stream = responseStatus < 400 ? connection.inputStream : connection.errorStream
    responseBody = stream?.text ?: ""
}

Then(~/^the response status is (\d+)$/) { int status ->
    assert responseStatus == status
}

Then(~/^the page contains "([^"]*)"$/) { String text ->
    assert responseBody.contains(text)
}

Then(~/^the page is canonical at "([^"]*)"$/) { String url ->
    assert responseBody.contains("<link rel=\"canonical\" href=\"${url}\">")
}

Then(~/^the page is not indexed$/) { ->
    assert responseBody.contains('<meta name="robots" content="noindex">')
}

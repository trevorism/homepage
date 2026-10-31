package com.trevorism.gcloud

this.metaClass.mixin(io.cucumber.groovy.Hooks)
this.metaClass.mixin(io.cucumber.groovy.EN)

String baseUrl = System.getenv("ACCEPTANCE_BASE_URL") ?: "https://www.trevorism.com"
String crawlerUserAgent = "Mozilla/5.0 (compatible; ClaudeBot/1.0)"

int responseStatus
String responseBody
String responseLocation

def send = { String method, String path, boolean followRedirects, String jsonBody ->
    HttpURLConnection connection = new URL("${baseUrl}${path}").openConnection() as HttpURLConnection
    connection.requestMethod = method
    connection.instanceFollowRedirects = followRedirects
    connection.setRequestProperty("User-Agent", crawlerUserAgent)
    connection.setRequestProperty("Accept", "*/*")
    if (jsonBody) {
        connection.doOutput = true
        connection.setRequestProperty("Content-Type", "application/json")
        connection.outputStream.withWriter { it << jsonBody }
    }
    responseStatus = connection.responseCode
    responseLocation = connection.getHeaderField("Location")
    InputStream stream = responseStatus < 400 ? connection.inputStream : connection.errorStream
    responseBody = stream?.text ?: ""
}

When(~/^a crawler requests "([^"]*)"$/) { String path ->
    send("GET", path, true, null)
}

When(~/^a crawler requests "([^"]*)" without following redirects$/) { String path ->
    send("GET", path, false, null)
}

When(~/^an assessment request without a valid email is submitted$/) { ->
    send("POST", "/api/assessment", true, '{"name":"Pat","email":"not-an-email"}')
}

Then(~/^the response status is (\d+)$/) { int status ->
    assert responseStatus == status
}

Then(~/^it redirects to "([^"]*)"$/) { String location ->
    assert responseLocation == location
}

Then(~/^the page contains "([^"]*)"$/) { String text ->
    assert responseBody.contains(text)
}

Then(~/^the page is canonical at "([^"]*)"$/) { String url ->
    assert responseBody.contains("<link rel=\"canonical\" href=\"${url}\">")
}

Then(~/^the page's stylesheet carries the header bar's styles$/) { ->
    String stylesheetPath = (responseBody =~ /href="(\/assets\/[^"]+\.css)"/)[0][1]
    String stylesheet = new URL("${baseUrl}${stylesheetPath}").text
    assert stylesheet.contains(".mini-menu[data-v-")
}

Then(~/^the page is not indexed$/) { ->
    assert responseBody.contains('<meta name="robots" content="noindex">')
}

package com.trevorism.gcloud.webapi.model

import groovy.transform.TupleConstructor
import io.micronaut.http.HttpStatus

@TupleConstructor
class ResolvedPage {

    HttpStatus status
    URL document
    String redirectTo

}

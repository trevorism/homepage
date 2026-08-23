package com.trevorism.gcloud.webapi.model

import groovy.transform.ToString

@ToString
class Tenant {

    String id
    String name
    String domain
    String guid
    String billingMode
    String status

    static Tenant NULL_TENANT = new Tenant()
    static boolean isNullTenant(Tenant tenant) {
        return !(tenant?.guid)
    }
}

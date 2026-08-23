package com.trevorism.gcloud.webapi.model

import groovy.transform.ToString

@ToString
class User {

    String id
    String username
    String email
    boolean admin
    boolean active
    String tenantGuid

    static User NULL_USER = new User()
    static boolean isNullUser(User user) {
        return !(user?.username)
    }
}

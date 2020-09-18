package com.trevorism.gcloud.webapi.model

import groovy.transform.ToString

@ToString
class User {

    String id
    String username
    String email
    String image

    String password
    String salt

    boolean admin

    boolean active
    Date dateCreated
    Date dateExpired

    @Override
    String getIdentifer() {
        return username
    }

    static User NULL_USER = new User()
    static boolean isNullUser(User user) {
        return !(user?.username)
    }
}

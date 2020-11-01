package com.trevorism.gcloud.webapi.service

import com.trevorism.https.DefaultSecureHttpClient
import com.trevorism.https.SecureHttpClient

class SecureHttpClientSingleton {
    private final SecureHttpClient secureHttpClient = new DefaultSecureHttpClient()

    private static SecureHttpClientSingleton INSTANCE

    static SecureHttpClientSingleton getInstance(){
        if(!INSTANCE){
            INSTANCE = new SecureHttpClientSingleton()
        }
        return INSTANCE
    }

    private SecureHttpClientSingleton(){}

    SecureHttpClient getSecureHttpClient() {
        return secureHttpClient
    }
}

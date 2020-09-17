package com.trevorism.gcloud.webapi.service

class Localhost {

    static boolean isLocal(){
        if (System.getenv("LOCALHOST_COOKIES"))
            return true
        return false
    }
}

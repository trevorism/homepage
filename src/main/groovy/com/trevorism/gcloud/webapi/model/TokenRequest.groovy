package com.trevorism.gcloud.webapi.model

class TokenRequest {
    String id
    String password
    String type = "user"

    static TokenRequest fromLoginRequest(LoginRequest request){
        return new TokenRequest(id: request.username, password: request.password)
    }
}

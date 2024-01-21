package com.example.streamingamazing.client

import com.example.streamingamazing.model.GoogleSignInAccessToken
import retrofit2.http.GET
import retrofit2.http.Query

interface  HttpAuthClient {

    @GET("/tokeninfo")
    suspend fun getTokenInfoAuthorization(
        @Query("id_token") idToken: String,
    ): GoogleSignInAccessToken

}
package com.example.streamingamazing.model

data class GoogleSignInAccessToken(
    val access_token: String,
    val expires_in: Int,
    val id_token: String,
    val token_type: String
)

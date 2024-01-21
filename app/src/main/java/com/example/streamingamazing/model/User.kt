package com.example.streamingamazing.model

import android.net.Uri


//criei um novo model com informacoes de string porque URI nao e aceito no adpater do moshi
data class UserModel(
    val accessToken: String?,
    val photo: Uri?,
    val givenName: String?,
    val email: String?,
    val authServeCode: String?,
    val idToken: String?
)


package com.example.streamingamazing.model

import android.net.Uri

data class UserModel(
    val accessToken: String?,
    val photo: Uri?,
    val givenName: String?,
    val email: String?
)
package com.example.streamingamazing.utility

import android.content.Context
import com.example.streamingamazing.BuildConfig
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope

fun getGoogleSignIn(context: Context): GoogleSignInClient {
    val clientId = BuildConfig.CLIENT_ID
    val googleSigIn =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
            //requestIdToken e do web server no google play console
            .requestIdToken(clientId)
            .requestScopes(
                Scope("https://www.googleapis.com/auth/youtube.force-ssl"),
                Scope("https://www.googleapis.com/auth/youtube.channel-memberships.creator"),
                Scope("https://www.googleapis.com/auth/youtube")
            )
            .requestServerAuthCode(
                clientId,
                true
            )
            .requestId().requestProfile().build()
    return GoogleSignIn.getClient(context, googleSigIn)
}
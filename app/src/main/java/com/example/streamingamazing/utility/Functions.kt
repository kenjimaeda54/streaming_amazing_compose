package com.example.streamingamazing.utility

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope

fun getGoogleSignIn(context: Context): GoogleSignInClient {
    val googleSigIn =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
            //requestIdToken e do web server no google play console
            .requestIdToken("46079490013-en3bvel31eb51eif6oakeptareoo2k4q.apps.googleusercontent.com")
            .requestScopes(
                Scope("https://www.googleapis.com/auth/youtube.force-ssl"),
                Scope("https://www.googleapis.com/auth/youtube.channel-memberships.creator"),
                Scope("https://www.googleapis.com/auth/youtube")
            )
            .requestServerAuthCode(
                "46079490013-en3bvel31eb51eif6oakeptareoo2k4q.apps.googleusercontent.com",
                true
            )
            .requestId().requestProfile().build()
    return GoogleSignIn.getClient(context, googleSigIn)
}
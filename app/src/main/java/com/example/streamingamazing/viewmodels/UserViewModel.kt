package com.example.streamingamazing.viewmodels

import android.accounts.Account
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streamingamazing.model.UserModel
import com.example.streamingamazing.repository.HttpClientRepository
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


@HiltViewModel
class UserViewModel @Inject constructor(private var httpClientRepository: HttpClientRepository) :
    ViewModel() {
    private  val _user  = MutableStateFlow<UserModel?>(null)
    val user: StateFlow<UserModel?> get() = _user
    private val _isAnonymous  = MutableStateFlow<Boolean>(true)
    val isAnonymous: StateFlow<Boolean> get() = _isAnonymous


    //https://developers.google.com/identity/sign-in/android/people?hl=pt-br
    fun getUserLogged(context: Context) {
        viewModelScope.launch {
            val account = GoogleSignIn.getLastSignedInAccount(context)
            if (account != null && account.serverAuthCode != null) {
//                val scopes =
//                    "https://www.googleapis.com/auth/youtube.force-ssl https://www.googleapis.com/auth/youtube.channel-memberships.creator https://www.googleapis.com/auth/youtube"
                val accessToken = httpClientRepository.fetchTokenGoogleAuth(
                    clientId = "46079490013-en3bvel31eb51eif6oakeptareoo2k4q.apps.googleusercontent.com",
                    clientSecret = "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",
                    serverCode = account.serverAuthCode!!
                )
                if (accessToken.data != null) {
                    _isAnonymous.value = false
                    _user.value = UserModel(
                        accessToken = accessToken.data!!.access_token,
                        photo = account.photoUrl,
                        givenName = account.givenName ?: account.displayName, email = account.email
                    )
                }


            }

        }


    }


}
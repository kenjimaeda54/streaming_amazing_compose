package com.example.streamingamazing.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streamingamazing.BuildConfig
import com.example.streamingamazing.data.DataOrException
import com.example.streamingamazing.model.TokenCacheInformationModel
import com.example.streamingamazing.model.UserModel
import com.example.streamingamazing.repository.HttpClientRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val httpClientRepository: HttpClientRepository,
    private val tokenCacheInformation: TokenCacheSharedPreferences
) :
    ViewModel() {
    private val _user = MutableStateFlow<DataOrException<UserModel, Boolean, Exception>>(
        DataOrException(data = null, true, Exception(""))
    )
    private val clientId = BuildConfig.CLIENT_ID
    private  val clientSecret = BuildConfig.CLIENT_SECRET
    val user: StateFlow<DataOrException<UserModel, Boolean, Exception>> get() = _user

    private val _isAnonymous = MutableStateFlow(true)
    val isAnonymous: StateFlow<Boolean> get() = _isAnonymous


    //https://developers.google.com/identity/sign-in/android/people?hl=pt-br
    fun getUserLogged(context: Context) {
        viewModelScope.launch {
            val account = GoogleSignIn.getLastSignedInAccount(context)
            val tokenInfo = tokenCacheInformation.getTokenInfo()

            if (tokenInfo != null) {
                val dataIsValidToken = httpClientRepository.isValidToken(tokenInfo.idToken!!)


                if(dataIsValidToken.data != null) {
                    _isAnonymous.value = false
                    account?.let {
                        val user = UserModel(
                            accessToken = tokenInfo.accessToken,
                            photo = account.photoUrl,
                            givenName = account.givenName ?: account.displayName,
                            email = account.email,
                            authServeCode = tokenInfo.authServeCode,
                            idToken = tokenInfo.idToken,
                        )
                        _user.value =
                            DataOrException(data = user, isLoading = false, exception = null)
                    }
                }else {
                    _user.value =
                        DataOrException(data = null, isLoading = false, exception = null)
                    tokenCacheInformation.clearTokenInfo()
                }


            } else if (account != null && account.serverAuthCode != null) {
                val googleAuth = httpClientRepository.fetchTokenGoogleAuth(
                    clientId = clientId,
                    clientSecret = clientSecret,
                    serverCode = account.serverAuthCode!!
                )

                if (googleAuth.data != null) {
                    val newTokenInfo = TokenCacheInformationModel(
                        accessToken = googleAuth.data?.access_token,
                        authServeCode = account.serverAuthCode,
                        idToken = googleAuth.data?.id_token
                    )
                    tokenCacheInformation.saveTokenInfo(newTokenInfo)
                    _isAnonymous.value = false
                    val user = UserModel(
                        accessToken = googleAuth.data!!.access_token,
                        photo = account.photoUrl,
                        givenName = account.givenName ?: account.displayName,
                        email = account.email,
                        authServeCode = account.serverAuthCode!!,
                        idToken = account.idToken,
                    )
                    _user.value = DataOrException(data = user, isLoading = false, exception = null)
                }
            } else {
                _user.value = DataOrException(data = null, isLoading = false, exception = null)
            }


        }

    }

    fun signOutUser() {
        tokenCacheInformation.clearTokenInfo()
        _user.value = DataOrException(data = null, isLoading = false, exception = null)
    }


}

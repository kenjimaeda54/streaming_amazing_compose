package com.example.streamingamazing.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.streamingamazing.model.TokenCacheInformationModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

interface  ITokenSharedPreferences {
    fun  saveTokenInfo(tokenInfo: TokenCacheInformationModel)
    fun getTokenInfo(): TokenCacheInformationModel?
    fun clearTokenInfo()

}

@HiltViewModel
class TokenCacheSharedPreferences @Inject constructor(private  val sharedPreferences: SharedPreferences): ViewModel(),
    ITokenSharedPreferences {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private  val adapter = moshi.adapter(TokenCacheInformationModel::class.java)



    override fun saveTokenInfo(tokenInfo: TokenCacheInformationModel) {
        sharedPreferences.edit().putString("tokenInfo",adapter.toJson(tokenInfo)).apply()
    }

    override fun getTokenInfo(): TokenCacheInformationModel? {
        val json = sharedPreferences.getString("tokenInfo",null) ?: return null
        return adapter.fromJson(json)
    }

    override fun clearTokenInfo() {
        sharedPreferences.edit().remove("tokenInfo").apply()
    }

}
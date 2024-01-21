package com.example.streamingamazing.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.streamingamazing.client.HttpAuthClient
import com.example.streamingamazing.client.HttpGoogleApisClient
import com.example.streamingamazing.utility.Constants
import com.example.streamingamazing.viewmodels.TokenCacheSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun streamingAmazingHttpGoogleApisClient(): HttpGoogleApisClient =
        Retrofit.Builder().baseUrl(Constants.baseUrlGoogleApi)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(HttpGoogleApisClient::class.java)


    @Provides
    @Singleton
    fun streamingAmazingHttpGoogleAuthClient(): HttpAuthClient =
        Retrofit.Builder().baseUrl(Constants.baseUrlAuthApi)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(HttpAuthClient::class.java)

    @Provides
    @Singleton
    fun streamingAmazingProvideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("session_prefs", MODE_PRIVATE)

    @Provides
    @Singleton
    fun streamingAmazingTokenCacheInformation(sharedPreferences: SharedPreferences): TokenCacheSharedPreferences =
        TokenCacheSharedPreferences(sharedPreferences)

}
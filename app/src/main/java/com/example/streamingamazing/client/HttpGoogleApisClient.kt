package com.example.streamingamazing.client

import com.example.streamingamazing.model.ChannelModel
import com.example.streamingamazing.model.GoogleSignInAccessToken
import com.example.streamingamazing.model.SubscriptionModel
import com.example.streamingamazing.model.VideoModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface HttpGoogleApisClient {
    ///search?part=snippet&relevanceLanguage=pt&maxResults=10&videoDuration=medium&type=video&regionCode=BR&key=${API_KEY}`
    @GET("/youtube/v3/search?part=snippet&relevanceLanguage=pt&maxResults=10&videoDuration=medium&type=video&regionCode=BR&key=AIzaSyAVxRrP61Dw76EUidoiPpfavIdqN62_LBw")
    suspend fun searchVideos(): VideoModel

    @GET("/youtube/v3/channels?part=statistics&part=snippet&key=AIzaSyAVxRrP61Dw76EUidoiPpfavIdqN62_LBw")
    suspend fun searchChannel(
        @Query("id") channelId: String
    ): ChannelModel


    @GET("/youtube/v3/subscriptions?part=snippet&maxResults=10&mine=true&key=AIzaSyAVxRrP61Dw76EUidoiPpfavIdqN62_LBw")
    suspend fun fetchChannelSubscriptions(
        @HeaderMap headers: Map<String,String>
    ): SubscriptionModel


    @FormUrlEncoded
    @POST("/oauth2/v4/token")
    suspend fun getTokenAuthorization(
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") serverCode: String,
    ): GoogleSignInAccessToken



}
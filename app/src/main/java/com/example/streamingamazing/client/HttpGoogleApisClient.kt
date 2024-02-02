package com.example.streamingamazing.client

import com.example.streamingamazing.model.ChannelModel
import com.example.streamingamazing.model.GoogleSignInAccessToken
import com.example.streamingamazing.model.PlayListIdsVideosChannel
import com.example.streamingamazing.model.PlaylistItemsChannel
import com.example.streamingamazing.model.SubscriptionModel
import com.example.streamingamazing.model.VideoDetailsModel
import com.example.streamingamazing.model.VideoModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Query

interface HttpGoogleApisClient {
    ///search?part=snippet&relevanceLanguage=pt&maxResults=10&videoDuration=medium&type=video&regionCode=BR&key=${API_KEY}`
    @GET("/youtube/v3/search?part=snippet&relevanceLanguage=pt&maxResults=10&videoDuration=medium&type=video&regionCode=BR&key=AIzaSyCU7HV_2LRv3Z3Uf0Prvb2C7i_ob8j9cQU")
    suspend fun searchVideos(): VideoModel


    @GET("/youtube/v3/search?part=snippet&eventType=live&relevanceLanguage=pt&maxResults=10&type=video&regionCode=BR&key=AIzaSyCU7HV_2LRv3Z3Uf0Prvb2C7i_ob8j9cQU")
    suspend fun searchLives(): VideoModel

    @GET("/youtube/v3/channels?part=statistics&part=snippet&key=AIzaSyCU7HV_2LRv3Z3Uf0Prvb2C7i_ob8j9cQU")
    suspend fun searchChannel(
        @Query("id") channelId: String
    ): ChannelModel


    @GET("/youtube/v3/subscriptions?part=snippet&maxResults=10&mine=true&key=AIzaSyCU7HV_2LRv3Z3Uf0Prvb2C7i_ob8j9cQU")
    suspend fun fetchChannelSubscriptions(
        @HeaderMap headers: Map<String, String>
    ): SubscriptionModel

    @GET("/youtube/v3/videos?part=snippet&part=statistics&key=AIzaSyCU7HV_2LRv3Z3Uf0Prvb2C7i_ob8j9cQU")
    suspend fun fetchVideoDetails(
        @Query("id") videoId: String
    ): VideoDetailsModel


    @FormUrlEncoded
    @POST("/oauth2/v4/token")
    suspend fun getTokenAuthorization(
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") serverCode: String,
    ): GoogleSignInAccessToken


    @GET("/youtube/v3/playlists?part=id&maxResults=10&key=AIzaSyCU7HV_2LRv3Z3Uf0Prvb2C7i_ob8j9cQU")
    suspend fun fetchIdsPlayList(
        @Query("channelId") channelId: String
    ): PlayListIdsVideosChannel


    @GET("/youtube/v3/playlistItems?part=snippet&maxResults=1&key=AIzaSyCU7HV_2LRv3Z3Uf0Prvb2C7i_ob8j9cQU")
    suspend fun fetchPlayListChannel(
        @Query("playlistId") playlistId: String
    ): PlaylistItemsChannel


}
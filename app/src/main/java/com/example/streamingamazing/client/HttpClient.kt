package com.example.streamingamazing.client

import com.example.streamingamazing.model.ChannelModel
import com.example.streamingamazing.model.SubscriptionModel
import com.example.streamingamazing.model.VideoModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.Query

interface HttpClient {
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

}
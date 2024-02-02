package com.example.streamingamazing.repository


import android.util.Log
import com.example.streamingamazing.client.HttpAuthClient
import com.example.streamingamazing.client.HttpGoogleApisClient
import com.example.streamingamazing.data.DataOrException
import com.example.streamingamazing.model.ChannelModel
import com.example.streamingamazing.model.GoogleSignInAccessToken
import com.example.streamingamazing.model.ResourceIdPlaylist
import com.example.streamingamazing.model.SnippetPlayList
import com.example.streamingamazing.model.SubscriptionModel
import com.example.streamingamazing.model.ThumbNails
import com.example.streamingamazing.model.VideoDetailsModel
import com.example.streamingamazing.model.VideosWithChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


class HttpClientRepository @Inject constructor(
    private val httpGoogleApisClient: HttpGoogleApisClient,
    private val httpAuthClient: HttpAuthClient
) {

    //multiples request
    //https://medium.com/@sribanavasi/handling-multiple-api-calls-in-android-best-practices-a95227f5f314

    suspend fun fetchVideosWithChannel(completion: (DataOrException<List<VideosWithChannel>, Boolean, Exception>) -> Unit) {
        try {
            val response = httpGoogleApisClient.searchVideos()
            CoroutineScope(Dispatchers.IO).launch {

                val videosWithChannel: List<VideosWithChannel> = response.items.map {
                    val channel =
                        async { httpGoogleApisClient.searchChannel(it.snippet.channelId) }.await()
                    VideosWithChannel(
                        descriptionVideo = it.snippet.description,
                        channelId = channel.items.first().id,
                        id = UUID.randomUUID().toString(),
                        publishedVideo = it.snippet.publishedAt,
                        thumbVideo = it.snippet.thumbnails.high.url,
                        subscriberCountChannel = channel.items.first().statistics.subscriberCount,
                        thumbProfileChannel = channel.items.first().snippet.thumbnails.medium.url,
                        titleVideo = it.snippet.title,
                        videoId = it.id.videoId
                    )

                }
                completion(DataOrException(data = videosWithChannel))
            }
        } catch (exception: Exception) {
            Log.d("Error", exception.message.toString())
            completion(DataOrException(exception = exception))
        }

    }

    suspend fun fetchChannel(channelId: String): DataOrException<ChannelModel, Boolean, Exception> {
        val response = try {
            httpGoogleApisClient.searchChannel(channelId)
        } catch (exception: Exception) {
            Log.d("Error", exception.message.toString())
            return DataOrException(exception = exception)
        }
        return DataOrException(data = response)
    }


    suspend fun fetchVideosLives(completion: (DataOrException<List<VideosWithChannel>, Boolean, Exception>) -> Unit) {
        try {
            val response = httpGoogleApisClient.searchLives()
            CoroutineScope(Dispatchers.IO).launch {

                val livesWithChannel: List<VideosWithChannel> = response.items.map {
                    val channel =
                        async { httpGoogleApisClient.searchChannel(it.snippet.channelId) }.await()
                    VideosWithChannel(
                        descriptionVideo = it.snippet.description,
                        channelId = channel.items.first().id,
                        id = UUID.randomUUID().toString(),
                        publishedVideo = it.snippet.publishedAt,
                        thumbVideo = it.snippet.thumbnails.high.url,
                        subscriberCountChannel = channel.items.first().statistics.subscriberCount,
                        thumbProfileChannel = channel.items.first().snippet.thumbnails.medium.url,
                        titleVideo = it.snippet.title,
                        videoId = it.id.videoId
                    )
                }
                completion(DataOrException(data = livesWithChannel))
            }
        } catch (exception: Exception) {
            Log.d("Error", exception.message.toString())
            completion(DataOrException(exception = exception))
        }

    }

    suspend fun fetchChannelSubscription(header: Map<String, String>): DataOrException<SubscriptionModel, Boolean, Exception> {
        val response = try {
            httpGoogleApisClient.fetchChannelSubscriptions(header)
        } catch (exception: Exception) {
            Log.d("Error", exception.message.toString())
            return DataOrException(exception = exception)
        }
        return DataOrException(data = response)
    }

    suspend fun fetchTokenGoogleAuth(
        clientId: String,
        clientSecret: String,
        serverCode: String
    ): DataOrException<GoogleSignInAccessToken, Boolean, Exception> {
        val response = try {
            httpGoogleApisClient.getTokenAuthorization(
                clientId = clientId,
                clientSecret = clientSecret,
                serverCode = serverCode
            )
        } catch (exception: Exception) {
            Log.d("Exception", exception.message.toString())
            return DataOrException(exception = exception)
        }
        return DataOrException(data = response)
    }

    suspend fun isValidToken(tokenInfo: String): DataOrException<Boolean, Boolean, Exception> {
        return try {
            httpAuthClient.getTokenInfoAuthorization(tokenInfo)
            DataOrException(data = true)
        } catch (exception: Exception) {
            Log.d("Exception", exception.message.toString())
            DataOrException(exception = exception)
        }

    }

    suspend fun fetchVideoDetails(videoId: String): DataOrException<VideoDetailsModel, Boolean, Exception> {
        val response = try {
            httpGoogleApisClient.fetchVideoDetails(videoId)
        } catch (exception: Exception) {
            Log.d("Exception", exception.message.toString())
            return DataOrException(exception = exception)
        }
        return DataOrException(data = response)
    }


    suspend fun fetchPlayListChannel(
        channelId: String,
        completion: (DataOrException<List<SnippetPlayList>, Boolean, Exception>) -> Unit
    ) {
        try {
            val response = httpGoogleApisClient.fetchIdsPlayList(channelId)
            CoroutineScope(Dispatchers.IO).launch {
                val listSnippet: List<SnippetPlayList> = response.items.map {
                    async { httpGoogleApisClient.fetchPlayListChannel(it.id) }.await()
                }.map {
                    SnippetPlayList(
                        title = it.items.first().snippet.title,
                        description = it.items.first().snippet.description,
                        publishedAt = it.items.first().snippet.publishedAt,
                        thumbnails = ThumbNails(
                            default = it.items.first().snippet.thumbnails.default,
                            medium = it.items.first().snippet.thumbnails.medium,
                            high = it.items.first().snippet.thumbnails.high,
                            standard = it.items.first().snippet.thumbnails.standard
                        ),
                        channelTitle = it.items.first().snippet.channelTitle,
                        resourceId = ResourceIdPlaylist(
                            videoId = it.items.first().snippet.resourceId.videoId
                        )
                    )
                }

                completion(DataOrException(data = listSnippet))
            }


        } catch (exception: Exception) {
            Log.d("Exception", exception.message.toString())
            completion(DataOrException(exception = exception))
        }

    }

}


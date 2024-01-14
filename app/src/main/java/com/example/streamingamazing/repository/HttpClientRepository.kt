package com.example.streamingamazing.repository


import android.provider.ContactsContract.Data
import android.util.Log
import com.example.streamingamazing.client.HttpClient
import com.example.streamingamazing.data.DataOrException
import com.example.streamingamazing.model.SubscriptionModel
import com.example.streamingamazing.model.VideosWithChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


class HttpClientRepository @Inject constructor(private val httpClient: HttpClient) {


    suspend fun fetchVideosWithChannel(completion: (DataOrException<List<VideosWithChannel>, Boolean, Exception>) -> Unit) {
        try {
            val response = httpClient.searchVideos()
            CoroutineScope(Dispatchers.IO).launch {

                val channelWithChannel: List<VideosWithChannel> = response.items.map {
                    val channel = async { httpClient.searchChannel(it.snippet.channelId) }.await()
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
                completion(DataOrException(data = channelWithChannel))
            }
        } catch (exception: Exception) {
            Log.d("Error", exception.toString())
            completion(DataOrException(exception = exception))
        }

    }

    suspend fun fetchChannelSubscription(header: Map<String, String>): DataOrException<SubscriptionModel, Boolean, Exception> {
        val response = try {
            httpClient.fetchChannelSubscriptions(header)
        } catch (excption: Exception) {
            Log.d("Error", excption.toString())
            return DataOrException(exception = excption)
        }
        return DataOrException(data = response)
    }

}
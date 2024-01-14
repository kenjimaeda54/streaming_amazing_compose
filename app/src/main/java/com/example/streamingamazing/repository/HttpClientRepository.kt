package com.example.streamingamazing.repository


import android.util.Log
import com.example.streamingamazing.client.HttpClient
import com.example.streamingamazing.data.DataOrException
import com.example.streamingamazing.model.ChannelModel
import com.example.streamingamazing.model.VideoModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class HttpClientRepository @Inject constructor(private val httpClient: HttpClient) {
    var requestsChannel: ArrayList<Observable<ChannelModel>> = ArrayList()


    suspend fun fetchVideos(): DataOrException<VideoModel, Boolean, Exception> {
        val response = try {
            httpClient.searchVideos()

        } catch (exception: Exception) {
            Log.d("Error", exception.toString())
            return DataOrException(exception = exception)
        }
        response.items.forEach {
            requestsChannel.add(
                httpClient.searchChannel(it.snippet.channelId).subscribeOn(Schedulers.io())
            )
        }
        return DataOrException(data = response)
    }


}
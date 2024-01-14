package com.example.streamingamazing.viewmodels


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streamingamazing.data.DataOrException
import com.example.streamingamazing.model.ChannelModel
import com.example.streamingamazing.model.VideoModel
import com.example.streamingamazing.model.VideosWithChannel
import com.example.streamingamazing.repository.HttpClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import io.reactivex.rxjava3.core.Observable;
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.internal.notifyAll


@HiltViewModel
class VideoWithChannelViewModel @Inject constructor(private val httpClientRepository: HttpClientRepository) :
    ViewModel() {
    private val _videosWithChannel = MutableStateFlow<DataOrException<MutableList<VideosWithChannel>, Boolean, Exception>>(DataOrException(null, true, Exception("")))
    val videosWithChannel = _videosWithChannel.asStateFlow()
    private  var _videosWithChannelMutableList = mutableListOf<VideosWithChannel>()


     fun  fetchVideos() {
        viewModelScope.launch {
            videosWithChannel.value.isLoading = true
            val videosData = httpClientRepository.fetchVideos()
            Observable.zip(httpClientRepository.requestsChannel) {
                //aqui que vai estar os resutlaados abaixo do subscribe e quando finaliza tudo
                val iteratorObject = it.iterator()
                while (iteratorObject.hasNext()) {
                    val channel = iteratorObject.next() as ChannelModel
                    if (videosData.data != null) {
                        val video =  videosData.data!!.items.first { video -> video.snippet.channelId == channel.items.first().id }
                        val channelWithVideo = VideosWithChannel(
                            descriptionVideo = video.snippet.description,
                            channelId = channel.items.first().id,
                            id = UUID.randomUUID().toString(),
                            publishedVideo = video.snippet.publishedAt,
                            thumbVideo = video.snippet.thumbnails.high.url,
                            subscriberCountChannel = channel.items.first().statistics.subscriberCount,
                            thumbProfileChannel = channel.items.first().snippet.thumbnails.medium.url,
                            titleVideo = video.snippet.title,
                            videoId = video.id.videoId
                        )
                        _videosWithChannel.value.data?.add(channelWithVideo)
                    }
                }
                Any   ()
            }.subscribe().
            videosWithChannel.value.isLoading = false
        }

    }



}
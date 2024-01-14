package com.example.streamingamazing.viewmodels


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streamingamazing.data.DataOrException
import com.example.streamingamazing.model.VideosWithChannel
import com.example.streamingamazing.repository.HttpClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VideoWithChannelViewModel @Inject constructor(private val httpClientRepository: HttpClientRepository) :
    ViewModel() {
    var videosWithChannel: MutableState<DataOrException<List<VideosWithChannel>, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, Exception("")))
    private var _videosMutableList = mutableListOf<VideosWithChannel>()

    fun fetchVideos() {
        viewModelScope.launch {
            videosWithChannel.value.isLoading = true
            httpClientRepository.fetchVideosWithChannel {
                videosWithChannel.value = it

                if (videosWithChannel.value.toString().isNotEmpty()) {
                    videosWithChannel.value.isLoading = false
                }
            }
        }


    }

}



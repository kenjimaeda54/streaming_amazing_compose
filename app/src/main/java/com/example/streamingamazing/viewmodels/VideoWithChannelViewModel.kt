package com.example.streamingamazing.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streamingamazing.data.DataOrException
import com.example.streamingamazing.model.VideosWithChannel
import com.example.streamingamazing.repository.HttpClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VideoWithChannelViewModel @Inject constructor(private val httpClientRepository: HttpClientRepository) :
    ViewModel() {
    private val _videosWithChannel =
        MutableStateFlow<DataOrException<List<VideosWithChannel>, Boolean, Exception>>(
            DataOrException(data = null, true, Exception(""))
        )
    private val _videoSelected = MutableStateFlow<VideosWithChannel?>(null)

    val videosWithChannel: StateFlow<DataOrException<List<VideosWithChannel>, Boolean, Exception>> get() = _videosWithChannel
    val videoSelected: StateFlow<VideosWithChannel?> get() = _videoSelected

    fun fetchVideos() {
        viewModelScope.launch {
            _videosWithChannel.value.isLoading = true
            httpClientRepository.fetchVideosWithChannel {
                _videosWithChannel.value = it

                if (_videosWithChannel.value.toString().isNotEmpty()) {
                    _videosWithChannel.value.isLoading = false
                }
            }
        }


    }

    fun fetchVideosWithLive() {
        viewModelScope.launch {
            _videosWithChannel.value.isLoading = false
            httpClientRepository.fetchVideosLives {
                _videosWithChannel.value = it

                if (_videosWithChannel.value.toString().isNotEmpty()) {
                    _videosWithChannel.value.isLoading = false
                }
            }
        }
    }

    fun handleVideoSelected(videosWithChannel: VideosWithChannel) {
        _videoSelected.value = videosWithChannel
    }

}



package com.example.streamingamazing.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streamingamazing.data.DataOrException
import com.example.streamingamazing.model.VideoDetailsModel
import com.example.streamingamazing.repository.HttpClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VideoDetailsViewModel @Inject constructor(private val httpClientRepository: HttpClientRepository) :
    ViewModel() {
    private val _videoDetails =
        MutableStateFlow<DataOrException<VideoDetailsModel, Boolean, Exception>>(
            DataOrException(data = null, true, Exception(""))
        )
    val videoDetails: StateFlow<DataOrException<VideoDetailsModel, Boolean, Exception>> get() = _videoDetails


    fun fetchVideoDetails(videoId: String) {
        viewModelScope.launch {
            val response = httpClientRepository.fetchVideoDetails(videoId)

            _videoDetails.value.isLoading = false

            if (response.data.toString().isNotEmpty()) {
                _videoDetails.value = response
            }

        }
    }
}
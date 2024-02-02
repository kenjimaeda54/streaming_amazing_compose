package com.example.streamingamazing.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streamingamazing.data.DataOrException
import com.example.streamingamazing.model.ChannelModel
import com.example.streamingamazing.repository.HttpClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChannelViewModel @Inject constructor(private val httpClientRepository: HttpClientRepository) :
    ViewModel() {
    private val _channel = MutableStateFlow<DataOrException<ChannelModel, Boolean, Exception>>(
        DataOrException(null, true, Exception(""))
    )
    val channel: StateFlow<DataOrException<ChannelModel, Boolean, Exception>> get() = _channel


    fun fetchChannel(channelId: String) {
        viewModelScope.launch {
            val response = httpClientRepository.fetchChannel(channelId)

            _channel.value = response
            _channel.value.isLoading = response.data == null

        }
    }


}

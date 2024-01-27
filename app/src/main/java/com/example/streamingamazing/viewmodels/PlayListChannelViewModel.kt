package com.example.streamingamazing.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streamingamazing.data.DataOrException
import com.example.streamingamazing.model.SnippetPlayList
import com.example.streamingamazing.repository.HttpClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PlayListChannelViewModel @Inject constructor(private val httpClientRepository: HttpClientRepository) :
    ViewModel() {
    private val _playList =
        MutableStateFlow<DataOrException<List<SnippetPlayList>, Boolean, Exception>>(
            DataOrException(
                data = null,
                true,
                Exception("")
            )
        )
    val playList: StateFlow<DataOrException<List<SnippetPlayList>, Boolean, Exception>> get() = _playList

    fun fetchPlayList(channelId: String) {
        viewModelScope.launch {
            httpClientRepository.fetchPlayListChannel(channelId) {
                _playList.value = it

                _playList.value.isLoading = it.data == null
            }


        }

    }

}
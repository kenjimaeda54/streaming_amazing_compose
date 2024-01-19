package com.example.streamingamazing.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streamingamazing.data.DataOrException
import com.example.streamingamazing.model.SubscriptionModel
import com.example.streamingamazing.repository.HttpClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(private val httpClientRepository: HttpClientRepository) :
    ViewModel() {
    private  val _data = MutableStateFlow<DataOrException<SubscriptionModel, Boolean, Exception>>(
        DataOrException(data = null, true, Exception(""))
    )
    val data: StateFlow<DataOrException<SubscriptionModel, Boolean, Exception>> get() = _data


    fun fetchSubscription(header: Map<String, String>) {
        viewModelScope.launch {
            val response = httpClientRepository.fetchChannelSubscription(header)

            if (response.data.toString().isNotEmpty()) {
                _data.value.isLoading = false
                _data.value = response
            }

        }

    }

}
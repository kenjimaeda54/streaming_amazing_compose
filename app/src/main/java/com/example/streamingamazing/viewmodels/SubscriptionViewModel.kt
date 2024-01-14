package com.example.streamingamazing.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streamingamazing.data.DataOrException
import com.example.streamingamazing.model.SubscriptionModel
import com.example.streamingamazing.repository.HttpClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(private val httpClientRepository: HttpClientRepository) :
    ViewModel() {
    val data: MutableState<DataOrException<SubscriptionModel, Boolean, Exception>> = mutableStateOf(
        DataOrException(data = null, true, Exception(""))
    )


    fun fetchSubscription(header: Map<String, String>) {
        viewModelScope.launch {
            val response = httpClientRepository.fetchChannelSubscription(header)

            if (response.data.toString().isNotEmpty()) {
                data.value.isLoading = false
                data.value = response
            }

        }

    }

}
package com.example.streamingamazing.data

data class DataOrException<T, Bool, E : Exception>(
    var data: T? = null,
    var isLoading: Bool? = null,
    var exception: E? = null
)
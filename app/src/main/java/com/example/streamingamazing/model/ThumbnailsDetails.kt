package com.example.streamingamazing.model


data class  ThumbNails(
    val default: ThumbnailsDetails,
    val medium: ThumbnailsDetails,
    val high: ThumbnailsDetails,
    val standard: ThumbnailsDetails
)

data class ThumbnailsDetails(
    val url: String
)

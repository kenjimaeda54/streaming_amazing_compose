package com.example.streamingamazing.model


data class VideoDetailsModel(
    val items: List<ItemsVideoDetails>
)

data class ItemsVideoDetails(
    val snippet: SnippetVideo,
    val statistics: StatisticsVideoDetails
)

data class StatisticsVideoDetails(
    val viewCount: String
)
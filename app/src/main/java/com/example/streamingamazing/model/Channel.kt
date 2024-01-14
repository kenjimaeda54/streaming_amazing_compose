package com.example.streamingamazing.model

data class ChannelModel(
    val items: List<ItemsChannel>
)

data class ItemsChannel(
    val id: String,
    val snippet: SnippetChannel,
    val statistics: Statistics
)

data class SnippetChannel(
    val title: String,
    val description: String,
    val customUrl: String,
    val publishedAt: String,
    val thumbnails: ThumbNails
)

data class Statistics(
    val subscriberCount: String
)
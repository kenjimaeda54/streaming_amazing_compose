package com.example.streamingamazing.model

data class VideoModel(
    val items: List<ItemsVideoSnippet>
)

data class ItemsVideoSnippet(
    val id: IdVideoModel,
    val snippet: SnippetVideo,
)

data class IdVideoModel(
    val videoId: String
)

data class SnippetVideo(
    val publishedAt: String,
    val channelId: String,
    val title: String,
    val description: String,
    val thumbnails: ThumbNails,
    val channelTitle: String
)
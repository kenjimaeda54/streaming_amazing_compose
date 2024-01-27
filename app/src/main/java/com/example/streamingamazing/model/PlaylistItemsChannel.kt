package com.example.streamingamazing.model



data class PlaylistItemsChannel(
    val items: List<ItemsPlayList>
)

data class ItemsPlayList(
    val snippet: SnippetPlayList,
    val id: String
)

data class SnippetPlayList(
    val title: String,
    val description: String,
    val publishedAt: String,
    val thumbnails: ThumbNails,
    val channelTitle: String,
    val resourceId: ResourceIdPlaylist,
)

data class ResourceIdPlaylist(
    val videoId: String
)
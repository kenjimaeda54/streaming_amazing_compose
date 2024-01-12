package com.example.streamingamazing.model

data class SubscriptionModel(
    val items: List<ItemsSubscription>
)


data class ItemsSubscription(
    val id: String,
    val snippet: SnippetSubscription
)

data class SnippetSubscription(
    val title: String,
    val thumbnails: ThumbNailsSubscription,
    val resourceId: ResourceId
)

data class ResourceId(
    val channelId: String
)

data class ThumbNailsSubscription(
    val default: ThumbnailsDetails,
    val medium: ThumbnailsDetails,
    val high: ThumbnailsDetails
)


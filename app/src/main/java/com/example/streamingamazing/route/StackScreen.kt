package com.example.streamingamazing.route

import java.lang.IllegalArgumentException

enum class StackScreen {
    SigIn,
    DetailsVideo,
    DetailsChannel;

    companion object {
        fun fromRoute(route: String): StackScreen = when (route.substringBefore("/")) {
            SigIn.name -> SigIn
            DetailsVideo.name -> DetailsVideo
            DetailsChannel.name -> DetailsChannel
            else -> throw IllegalArgumentException("Route $route is not recognizable")
        }
    }
}
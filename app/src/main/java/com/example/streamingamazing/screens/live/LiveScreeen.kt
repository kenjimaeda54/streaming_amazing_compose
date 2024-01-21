package com.example.streamingamazing.screens.live

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.example.streamingamazing.model.VideosWithChannel
import com.example.streamingamazing.screens.home.view.RowVideosWithChannel
import com.example.streamingamazing.view.ComposableLifecycle
import com.example.streamingamazing.viewmodels.VideoWithChannelViewModel

@Composable
fun LiveScreen() {
    val videosWithChannel: VideoWithChannelViewModel = hiltViewModel()
    val lives by videosWithChannel.videosWithChannel.collectAsState()


    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_CREATE) {
            videosWithChannel.fetchVideosWithLive()
        }

    }

    if (lives.isLoading == true) {
        Text(text = "Is loading")
    } else {
        Surface(modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.secondary) {
            LazyColumn(
                modifier = Modifier.padding(start = 13.dp, top = 10.dp),
                contentPadding = PaddingValues(bottom = 30.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(lives.data!!) {
                    RowVideosWithChannel(video = it)
                }
            }
        }
    }
}
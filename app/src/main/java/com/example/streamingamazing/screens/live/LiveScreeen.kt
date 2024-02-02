package com.example.streamingamazing.screens.live

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.streamingamazing.mock.videosWithChannelMock
import com.example.streamingamazing.route.StackScreen
import com.example.streamingamazing.screens.home.view.RowVideosWithChannel
import com.example.streamingamazing.view.ComposableLifecycle
import com.example.streamingamazing.view.RowCardVideosPlaceholder
import com.example.streamingamazing.viewmodels.VideoWithChannelViewModel

@Composable
fun LiveScreen(navController: NavController, videosWithChannelModel: VideoWithChannelViewModel ) {
    val lives by videosWithChannelModel.videosWithChannel.collectAsState()


    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_START) {
            videosWithChannelModel.fetchVideosWithLive()
        }

    }

    if (lives.isLoading == true) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.secondary) {
            LazyColumn(
                modifier = Modifier.padding(start = 13.dp, top = 10.dp),
                contentPadding = PaddingValues(bottom = 30.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(videosWithChannelMock) {
                    RowCardVideosPlaceholder()
                }
            }
        }
    } else {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.secondary
        ) {
            LazyColumn(
                modifier = Modifier.padding(start = 13.dp, top = 10.dp),
                contentPadding = PaddingValues(bottom = 30.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(lives.data!!) {
                    RowVideosWithChannel(video = it, modifier = Modifier.clickable {
                        videosWithChannelModel.handleVideoSelected(it)
                        navController.navigate(StackScreen.DetailsVideo.name)
                    })
                }
            }
        }
    }
}
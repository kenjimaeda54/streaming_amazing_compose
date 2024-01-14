package com.example.streamingamazing.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.streamingamazing.mock.subscriptionDataMock
import com.example.streamingamazing.mock.videosWithChannelMock
import com.example.streamingamazing.screens.home.view.RowChannelSubscription
import com.example.streamingamazing.screens.home.view.RowVideosWithChannel
import com.example.streamingamazing.ui.theme.fontsLato
import com.example.streamingamazing.view.ComposableLifecycle
import com.example.streamingamazing.viewmodels.SubscriptionViewModel
import com.example.streamingamazing.viewmodels.VideoWithChannelViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    videoWithChannelViewModel: VideoWithChannelViewModel = hiltViewModel(),
    subscriptionViewModel: SubscriptionViewModel = hiltViewModel()
) {
    LocalOverscrollConfiguration provides null
    val configuration = LocalConfiguration.current.screenWidthDp
    val spacing = (configuration * 0.043).dp

    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_CREATE) {
            videoWithChannelViewModel.fetchVideos()
            val header: Map<String, String> = mapOf("Authorization" to "Bearer fosnfos")
            subscriptionViewModel.fetchSubscription(header)
        }

    }

    if (videoWithChannelViewModel.videosWithChannel.value.isLoading == true) {
        Text(text = "loading")
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
                stickyHeader {
                    Surface { //surface e para remover a cor transparente
                        Column(
                            modifier = Modifier
                                .height(180.dp) // porque tem o lazyHorizontalGrid por isso precisa da altura
                            , verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data("https://github.com/kenjimaeda54.png").build(),
                                    contentDescription = "Image avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape)
                                )
                                Spacer(modifier = Modifier.padding(horizontal = 7.dp))
                                Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                                    Text(
                                        text = "Bem vindo de volta 👋",
                                        fontFamily = fontsLato,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = "Kenji",
                                        fontFamily = fontsLato,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                            LazyHorizontalGrid(
                                rows = GridCells.Fixed(1),
                                modifier = Modifier.height(100.dp), //tem que definir altura dele tambem
                                horizontalArrangement = Arrangement.spacedBy(spacing),
                            ) {
                                items(subscriptionDataMock.items) {
                                    RowChannelSubscription(snippet = it.snippet)
                                }
                            }
                        }
                    }


                }

                items(videoWithChannelViewModel.videosWithChannel.value.data!!) {
                    RowVideosWithChannel(video = it)
                }

            }


        }
    }


}
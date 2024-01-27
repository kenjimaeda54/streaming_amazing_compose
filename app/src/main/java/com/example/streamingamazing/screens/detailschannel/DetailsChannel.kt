package com.example.streamingamazing.screens.detailschannel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.streamingamazing.model.ItemsSubscription
import com.example.streamingamazing.model.VideosWithChannel
import com.example.streamingamazing.route.StackScreen
import com.example.streamingamazing.screens.detailschannel.view.RowVideoChannel
import com.example.streamingamazing.ui.theme.fontsLato
import com.example.streamingamazing.utility.BottomBarScreen
import com.example.streamingamazing.view.BackButton
import com.example.streamingamazing.view.ComposableLifecycle
import com.example.streamingamazing.viewmodels.ChannelViewModel
import com.example.streamingamazing.viewmodels.PlayListChannelViewModel
import com.example.streamingamazing.viewmodels.VideoWithChannelViewModel
import java.util.UUID

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsChannel(
    channelSubscription: ItemsSubscription,
    videoWithChannelViewModel: VideoWithChannelViewModel,
    navController: NavController
) {
    val playListChannelViewModel: PlayListChannelViewModel = hiltViewModel()
    val channelViewModel: ChannelViewModel = hiltViewModel()

    val channel by channelViewModel.channel.collectAsState()
    val playlist by playListChannelViewModel.playList.collectAsState()
    val spacing = (LocalConfiguration.current.screenWidthDp * 0.25).dp


    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_CREATE) {
            playListChannelViewModel.fetchPlayList(channelSubscription.snippet.resourceId.channelId)
            channelViewModel.fetchChannel(channelSubscription.snippet.resourceId.channelId)
        }

    }

    if (playlist.isLoading == true && channel.isLoading == true) {
        Text(text = "loading")
    } else {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.secondary
        ) {
            LazyColumn(
                modifier = Modifier.padding(top = 10.dp),
                contentPadding = PaddingValues(bottom = 30.dp, start = 13.dp, end = 13.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                stickyHeader {
                    Surface {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BackButton(
                                modifier = Modifier.clickable {
                                    navController.popBackStack()
                                },
                                cardColors = CardDefaults.cardColors(
                                    containerColor = Color.Black.copy(0.2f),
                                )
                            )
                            Spacer(modifier = Modifier.width(spacing))
                            AsyncImage(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(channelSubscription.snippet.thumbnails.medium.url)
                                    .build(),
                                contentDescription = "Image avatar channelSubscription",
                                contentScale = ContentScale.Crop,
                            )
                            Spacer(modifier = Modifier.width(7.dp))
                            Text(
                                text = channelSubscription.snippet.title,
                                fontFamily = fontsLato,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                items(playlist.data!!) { playList ->
                    RowVideoChannel(modifier = Modifier.clickable {
                        val video = VideosWithChannel(
                            thumbVideo = playList.thumbnails.high.url,
                            thumbProfileChannel = channelSubscription.snippet.thumbnails.medium.url,
                            channelId = channelSubscription.snippet.resourceId.channelId,
                            descriptionVideo = channel.data!!.items.first().snippet.description,
                            id = UUID.randomUUID().toString(),
                            publishedVideo = playList.publishedAt,
                            subscriberCountChannel = channel.data!!.items.first().statistics.subscriberCount,
                            titleVideo = playList.title,
                            videoId = playList.resourceId.videoId

                        )
                        videoWithChannelViewModel.handleVideoSelected(video)
                        navController.navigate(StackScreen.DetailsVideo.name) {
                            popUpTo(BottomBarScreen.Home.route) //aqui navego e limpo as rotas, quando clicar para voltar na proxima rota retorno a home
                        }
                    }, channel = playList)
                }

            }
        }
    }

}
package com.example.streamingamazing.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.streamingamazing.route.StackScreen
import com.example.streamingamazing.screens.home.view.RowChannelSubscription
import com.example.streamingamazing.screens.home.view.RowVideosWithChannel
import com.example.streamingamazing.ui.theme.fontsLato
import com.example.streamingamazing.view.ComposableLifecycle
import com.example.streamingamazing.viewmodels.SubscriptionViewModel
import com.example.streamingamazing.viewmodels.UserViewModel
import com.example.streamingamazing.viewmodels.VideoWithChannelViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController) {
    LocalOverscrollConfiguration provides null //para remover comportamento de bounce
    val videoWithChannelViewModel: VideoWithChannelViewModel = hiltViewModel()
    val videosWithChannel by videoWithChannelViewModel.videosWithChannel.collectAsState()

    val subscriptionViewModel: SubscriptionViewModel = hiltViewModel()
    val subscription by subscriptionViewModel.data.collectAsState()

    val userViewModel: UserViewModel = hiltViewModel()
    val user by userViewModel.user.collectAsState()

    val context = LocalContext.current
    val configuration = LocalConfiguration.current.screenWidthDp
    val spacing = (configuration * 0.043).dp


    //com launchedEffect consigo acompanhar as mudancas por isso coloquei o user que um stateFlow
    LaunchedEffect(user) {
        user.data?.accessToken?.let {
            val header: Map<String, String> =
                mapOf("Authorization" to "Bearer $it")
            subscriptionViewModel.fetchSubscription(header)
        }


    }

    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_CREATE) {
            userViewModel.getUserLogged(context)
            videoWithChannelViewModel.fetchVideos()
        }

    }



    if (videosWithChannel.isLoading == true || subscription.isLoading == true) {
        Text(text = "loading")
    } else if (user.data?.accessToken != null && subscription.exception != null) {
        navController.navigate(StackScreen.SigIn.name)
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
                stickyHeader { //stikcyHeader e o header mesma ideia do Flatlisth, listHeaderComponent
                    Surface { //surface e para remover a cor transparente
                        Column(
                            modifier = Modifier
                                .height(180.dp)// porque tem o lazyHorizontalGrid por isso precisa da altura
                            , verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(user.data?.photo).build(),
                                    contentDescription = "Image avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape)
                                )
                                Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                                    Text(
                                        text = "Bem vindo de volta 👋",
                                        fontFamily = fontsLato,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    user.data?.givenName?.let {
                                        Text(
                                            text = it,
                                            fontFamily = fontsLato,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                            LazyHorizontalGrid(
                                rows = GridCells.Fixed(1),
                                modifier = Modifier.height(100.dp), //tem que definir altura dele tambem
                                horizontalArrangement = Arrangement.spacedBy(spacing),
                            ) {
                                items(subscription.data!!.items) {
                                    RowChannelSubscription(snippet = it.snippet)
                                }
                            }
                        }
                    }


                }

                items(videosWithChannel.data!!) {
                    RowVideosWithChannel(video = it, modifier = Modifier.clickable {
                        videoWithChannelViewModel.handleVideoSelected(it)
                        navController.navigate(StackScreen.DetailsVideo.name)
                    })
                }

            }


        }
    }


}
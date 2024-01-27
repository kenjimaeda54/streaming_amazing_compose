package com.example.streamingamazing.screens.detailsVideo

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.streamingamazing.screens.detailsVideo.view.YoutubeView
import com.example.streamingamazing.ui.theme.fontsLato
import com.example.streamingamazing.view.AvatarPlaceHolder
import com.example.streamingamazing.view.BackButton
import com.example.streamingamazing.view.ComposableLifecycle
import com.example.streamingamazing.view.PreviewYoutubePlaceHolder
import com.example.streamingamazing.view.TitlePlaceHolder
import com.example.streamingamazing.viewmodels.VideoDetailsViewModel
import com.example.streamingamazing.viewmodels.VideoWithChannelViewModel
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.pow


@Composable
fun DetailsVideo(
    videoWithChannelViewModel: VideoWithChannelViewModel,
    navController: NavController
) {
    val configuration = LocalConfiguration.current
    val videoDetailsViewModel: VideoDetailsViewModel = hiltViewModel()
    val videoDetails by videoDetailsViewModel.videoDetails.collectAsState()
    val videoSelected by videoWithChannelViewModel.videoSelected.collectAsState()
    val heightDescription = (configuration.screenHeightDp * 0.5).dp
    val marginTop = (configuration.screenHeightDp * 0.06).dp


    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_CREATE) {
            videoSelected?.videoId?.let { videoDetailsViewModel.fetchVideoDetails(it) }
        }

    }

    fun currentDate(): Long {
        val calendar = Calendar.getInstance()
        return calendar.timeInMillis
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDate(value: String): String {
        val second = 1
        val minute = 60 * second
        val hour = 60 * minute
        val day = 24 * hour
        val month = 30 * day
        val year = 12 * month
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        return try {
            val time = format.parse(value)!!.time
            val now = currentDate()
            val diff = (now - time) / 1000


            return when {
                diff < minute -> "Agora"
                diff < 60 * minute -> "Um minuto atrás"
                diff < 2 * hour -> "Uma hora atrás"
                diff < 24 * hour -> "${diff / hour} horas atrás"
                diff < 30 * day -> "${diff / day} dias atrás"
                diff < 2 * month -> "Um mes atrás"
                diff < 2 * year -> "Um ano atrás"
                else -> "${diff / year} anos atrás"
            }

        } catch (exception: Exception) {
            "Error ao formatar data"
        }

    }


    fun formatQuantityView(value: String): String {
        val symbol = listOf("", "mil", "mi", "b", "t", "p", "e")
        //operator bitwise so aceita long or int
        //https://www.programiz.com/kotlin-programming/bitwise
        val tier = log10(abs(value.toDouble())).toInt() / 3 or 0

        if (tier == 0) {
            return value
        }

        val suffix = symbol[tier]
        val scale = 10.0.pow(tier * 3)
        val scaled = value.toDouble() / scale
        return "${scaled.toBigDecimal().setScale(1, RoundingMode.FLOOR).toDouble()} $suffix"
    }

    if (videoSelected == null) {
        Text(text = "error")
    } else if (videoDetails.isLoading == true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.secondary
        ) {
            Column {
                PreviewYoutubePlaceHolder()
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(vertical = 10.dp, horizontal = 13.dp),
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    TitlePlaceHolder(
                        modifier = Modifier
                            .height(20.dp)
                            .fillMaxWidth()
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        TitlePlaceHolder(
                            modifier = Modifier
                                .height(20.dp)
                                .width(65.dp)
                        )
                        TitlePlaceHolder(
                            modifier = Modifier
                                .height(20.dp)
                                .width(65.dp)
                        )
                        TitlePlaceHolder(
                            modifier = Modifier
                                .height(20.dp)
                                .width(65.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AvatarPlaceHolder()
                        TitlePlaceHolder(
                            modifier = Modifier
                                .height(20.dp)
                                .width(80.dp)
                        )
                        TitlePlaceHolder(
                            modifier = Modifier
                                .height(20.dp)
                                .width(50.dp)
                        )

                    }
                    TitlePlaceHolder(
                        modifier = Modifier
                            .height(heightDescription)
                            .fillMaxWidth()
                    )

                }
            }
        }
    } else {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.secondary
        ) {
            Column {
                Box {
                    YoutubeView(videoId = videoSelected!!.videoId)
                    BackButton(modifier = Modifier.padding(top = marginTop, start = 13.dp).clickable {
                        navController.popBackStack()
                    })
                }
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(vertical = 10.dp, horizontal = 13.dp),
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    Text(
                        text = videoSelected!!.titleVideo,
                        fontFamily = fontsLato,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        lineHeight = 25.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                        Text(
                            text = formatDate(videoSelected!!.publishedVideo),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.secondaryContainer
                        )
                        Text(
                            text = formatQuantityView(videoDetails.data!!.items.first().statistics.viewCount),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.secondaryContainer
                        )
                        Text(
                            text = videoDetails.data!!.items.first().snippet.channelTitle,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.secondaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(videoSelected!!.thumbProfileChannel)
                                .build(),
                            contentDescription = "Avatar channel",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(
                                    CircleShape
                                )
                        )
                        Text(
                            text = videoDetails.data!!.items.first().snippet.channelTitle,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            modifier = Modifier.padding(top = 2.dp),
                            text = formatQuantityView(videoSelected!!.subscriberCountChannel),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.secondaryContainer
                        )
                    }
                    Text(
                        text = videoDetails.data!!.items.first().snippet.description,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 25.sp,
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        overflow = TextOverflow.Visible
                    )

                }
            }


        }
    }
}








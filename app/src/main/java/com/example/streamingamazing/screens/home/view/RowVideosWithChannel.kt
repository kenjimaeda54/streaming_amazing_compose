package com.example.streamingamazing.screens.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.streamingamazing.model.VideosWithChannel
import com.example.streamingamazing.ui.theme.fontsLato

@Composable
fun RowVideosWithChannel(video: VideosWithChannel) {
    Column(modifier = Modifier.padding(end = 13.dp),verticalArrangement = Arrangement.spacedBy(8.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(video.thumbVideo).build(),
            contentDescription = "Thumb video",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(9.dp)
                )
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(video.thumbProfileChannel)
                    .build(),
                contentDescription = "Avatar channel",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(30.dp)
                    .clip(
                        CircleShape
                    )
            )
            Text(
                video.titleVideo,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontFamily = fontsLato,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }

    }
}
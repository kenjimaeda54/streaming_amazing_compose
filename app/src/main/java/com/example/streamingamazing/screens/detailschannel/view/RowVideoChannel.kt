package com.example.streamingamazing.screens.detailschannel.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.streamingamazing.model.SnippetPlayList
import com.example.streamingamazing.ui.theme.fontsLato

@Composable
fun RowVideoChannel(modifier: Modifier = Modifier, channel: SnippetPlayList) {
    Column(
        modifier = modifier.padding(bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(channel.thumbnails.high.url)
                .build(),
            contentDescription = "Thumb video",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(9.dp)
                )
        )
        Text(
            text = channel.title,
            fontFamily = fontsLato,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = channel.description,
            fontFamily = fontsLato,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            lineHeight = 17.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.primary
        )


    }
}


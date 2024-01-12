package com.example.streamingamazing.screens.home.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import com.example.streamingamazing.model.SnippetSubscription
import com.example.streamingamazing.ui.theme.fontsLato

@Composable
fun RowChannelSubscription(snippet: SnippetSubscription) {
    Column(modifier = Modifier.width(74.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(snippet.thumbnails.medium.url)
                .build(),
            contentDescription = "Image avatar channel",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )
        Text(
            text = snippet.title,
            fontFamily = fontsLato,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
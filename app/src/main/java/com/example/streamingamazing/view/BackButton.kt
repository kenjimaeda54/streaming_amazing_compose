package com.example.streamingamazing.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp


//blur hash
//https://github.com/woltapp/blurhash

@Composable
fun BackButton(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val marginTop = (configuration.screenHeightDp * 0.06).dp
    Card(
        modifier = modifier
            .padding(top = marginTop, start = 13.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = Color.Black.copy(0.6f),
        )

    ) {
        Image(
            modifier = Modifier.padding(vertical = 3.dp, horizontal = 3.dp),
            imageVector = Icons.Filled.KeyboardArrowLeft,
            contentDescription = "Icon back",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
        )
    }


}


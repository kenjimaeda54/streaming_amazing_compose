package com.example.streamingamazing.view

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.streamingamazing.modifier.shimmerBackground

@Composable
fun RootPlaceHolder() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .shimmerBackground(
                RoundedCornerShape(
                    5.dp
                )
            )
    ) {
        Text(text = "")
    }

}


@Composable
fun RowSubscriptions() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AvatarPlaceHolder()
        Spacer(modifier = Modifier.height(7.dp))
        TitlePlaceHolder(
            modifier = Modifier
                .height(15.dp)
                .width(50.dp)
        )
    }
}


@Composable
fun RowCardVideosPlaceholder() {
    Column(
        modifier = Modifier.padding(end = 13.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CardPlaceHolder()
        Row(
            horizontalArrangement = Arrangement.spacedBy(7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AvatarPlaceHolder(modifier = Modifier.size(30.dp))
            TitlePlaceHolder()
        }
    }
}

@Composable
fun RowVideoChannelPlaceHolder() {
    Column(
        modifier = Modifier.padding(bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ){
        CardPlaceHolder()
        TitlePlaceHolder(modifier = Modifier.height(35.dp).fillMaxWidth())
        TitlePlaceHolder(modifier = Modifier.height(45.dp).fillMaxWidth())
    }
}




@Composable
fun PreviewYoutubePlaceHolder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .shimmerBackground()
    )
}

@Composable
fun CardPlaceHolder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .shimmerBackground(RoundedCornerShape(9.dp))
    )
}

@Composable
fun TitlePlaceHolder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(25.dp)
            .width(150.dp)
            .shimmerBackground(RoundedCornerShape(5.dp))
    )
}


@Composable
fun AvatarPlaceHolder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(60.dp)
            .shimmerBackground(CircleShape)
    )

}



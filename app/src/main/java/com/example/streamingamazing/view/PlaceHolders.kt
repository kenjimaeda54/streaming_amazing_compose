package com.example.streamingamazing.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

@Preview
@Composable
fun RootPlaceHolderPreview() {
    RootPlaceHolder()
}
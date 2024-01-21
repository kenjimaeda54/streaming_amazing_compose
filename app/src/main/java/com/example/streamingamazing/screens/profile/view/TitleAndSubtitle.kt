package com.example.streamingamazing.screens.profile.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.streamingamazing.ui.theme.fontsLato

@Composable
fun TitleAndSubtitle(title: String, subTitle: String) {

    Column {
        Text(
            text = title,
            fontFamily = fontsLato,
            fontWeight = FontWeight.Bold,
            fontSize = 19.sp,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = subTitle, fontFamily = fontsLato,
            fontWeight = FontWeight.Light,
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.secondaryContainer
        )
    }
}


@Composable
@Preview
fun TitleAndSubtitlePreview() {
    TitleAndSubtitle(title = "Nome", subTitle = "Ricardo kenji")
}
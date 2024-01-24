package com.example.streamingamazing.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

private val colorScheme = darkColorScheme(
    primary = black100,
    secondary = white,
    tertiary = white100,
    error = red,
    secondaryContainer = gray100,
    primaryContainer = gray50
)


//composable theme
//https://medium.com/android-dev-br/criando-um-tema-customizável-com-jetpack-compose-3bb7b7a845ed
@Composable
fun StreamingAmazingTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = colorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
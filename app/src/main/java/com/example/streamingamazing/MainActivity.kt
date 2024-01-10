package com.example.streamingamazing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.streamingamazing.screens.RootScreen
import com.example.streamingamazing.ui.theme.StreamingAmazingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StreamingAmazingTheme {
                // A surface container using the 'background' color from the theme
                RootScreen()
            }
        }
    }
}


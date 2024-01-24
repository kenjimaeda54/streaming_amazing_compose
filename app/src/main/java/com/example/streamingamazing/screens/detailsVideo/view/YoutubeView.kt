package com.example.streamingamazing.screens.detailsVideo.view

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController


//como usar view ou xml dentro do compose
//https://developer.android.com/jetpack/compose/migrate/interoperability-apis/views-in-compose?hl=pt-br#:~:text=AndroidView%20is%20passed%20a%20lambda,read%20within%20the%20callback%20changes.


@Composable
fun YoutubeView(videoId: String) {
    val activity = LocalContext.current as Activity



    AndroidView(
        factory = {
            val view = YouTubePlayerView(it)
            view.enableAutomaticInitialization = false //precisa disso para nao crashar
            //coloque a palavra object porque e um abstrata classe
            view.addFullScreenListener(object : YouTubePlayerFullScreenListener {
                override fun onYouTubePlayerEnterFullScreen() {
                    activity.requestedOrientation =
                        ActivityInfo.SCREEN_ORIENTATION_SENSOR//para alterar o comportamento da view
                }

                override fun onYouTubePlayerExitFullScreen() {
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                }

            })
            val playerListener = object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)

                    //https://github.com/PierfrancescoSoffritti/android-youtube-player/issues/1037
                    val uiController = DefaultPlayerUiController(view, youTubePlayer)
                    uiController.showUi(true)
                    uiController.showDuration(true)
                    uiController.showFullscreenButton(true)
                    uiController.showSeekBar(true)
                    uiController.showBufferingProgress(true)
                    uiController.showCurrentTime(true)
                    uiController.showMenuButton(false)
                    uiController.showPlayPauseButton(true)
                    uiController.showVideoTitle(false)
                    uiController.showYouTubeButton(false)
                    view.setCustomPlayerUi(uiController.rootView)
                    youTubePlayer.loadVideo(videoId, 0f)

                }


            }
            val builder = IFramePlayerOptions.Builder()
                .controls(0) //se deixar isso fora de 0 ira aparecer um outro snack em cima
                .ccLoadPolicy(0)
                .rel(0)
                /*.fullscreen(1)*/
                .ivLoadPolicy(1)
            val options = builder.build()
            view.initialize(playerListener, true, options)
            view
        })

}



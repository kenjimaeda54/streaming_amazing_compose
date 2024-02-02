package com.example.streamingamazing.route

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.streamingamazing.screens.detailsVideo.DetailsVideo
import com.example.streamingamazing.screens.detailschannel.DetailsChannel
import com.example.streamingamazing.screens.home.HomeScreen
import com.example.streamingamazing.screens.live.LiveScreen
import com.example.streamingamazing.screens.profile.ProfileScreen
import com.example.streamingamazing.screens.sigIn.SigInScreen
import com.example.streamingamazing.utility.BottomBarScreen
import com.example.streamingamazing.viewmodels.SubscriptionViewModel
import com.example.streamingamazing.viewmodels.VideoWithChannelViewModel


//https://medium.com/@KaushalVasava/navigation-in-jetpack-compose-full-guide-beginner-to-advanced-950c1133740
@SuppressLint(
    "RememberReturnType", "UnrememberedGetBackStackEntry",
    "StateFlowValueCalledInComposition"
)
@Composable
fun NavGraphApp(navController: NavHostController, isAnonymous: Boolean) {
    NavHost(
        navController = navController,
        startDestination = if (isAnonymous) StackScreen.SigIn.name else BottomBarScreen.Home.route
    ) {
        composable(StackScreen.SigIn.name) {

            SigInScreen(navController)
        }
        composable(BottomBarScreen.Home.route, exitTransition = {
            return@composable slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(3000) 
            )
        }) {
            HomeScreen(navController)
        }

        composable(StackScreen.DetailsChannel.name, enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(3000)
            )
        }, exitTransition = {
            return@composable slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(3000)
            )
        }) { entry ->
            val parentEntryHome = remember(entry) {
                navController.getBackStackEntry(BottomBarScreen.Home.route)
            }
            val parentEntrySubscriptionViewModel =
                hiltViewModel<SubscriptionViewModel>(parentEntryHome)
            val parentEntryVideoWithChannelViewModel = hiltViewModel<VideoWithChannelViewModel>(parentEntryHome)
            parentEntrySubscriptionViewModel.channelSubscriptionSelected?.let {
                DetailsChannel(
                    channelSubscription = it,
                    videoWithChannelViewModel = parentEntryVideoWithChannelViewModel,
                    navController
                )
            }
        }

        composable(StackScreen.DetailsVideo.name, exitTransition = {
            return@composable slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(3000)
            )
        },) { entry ->

            val parentEntryHome = remember(entry) {
                navController.getBackStackEntry(BottomBarScreen.Home.route)
            }
            val parentEntryViewModel = hiltViewModel<VideoWithChannelViewModel>(parentEntryHome)

            DetailsVideo(
                videoWithChannelViewModel = parentEntryViewModel,
                navController
            )
        }

        composable(BottomBarScreen.Live.route) { entry ->
            val parentEntryHome = remember(entry) {
                navController.getBackStackEntry(BottomBarScreen.Home.route)
            }
            val parentEntryViewModel = hiltViewModel<VideoWithChannelViewModel>(parentEntryHome)
            LiveScreen(navController, parentEntryViewModel)
        }
        composable(BottomBarScreen.Profile.route, exitTransition = {
            //repara que para sair e slideOut
            //https://tomas-repcik.medium.com/jetpack-compose-and-screen-transition-animations-b361fc8164cc
            return@composable slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Down,
                tween(3000)
            )
        }) {
            ProfileScreen(navController)
        }
    }
}
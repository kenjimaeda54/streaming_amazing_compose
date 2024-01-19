package com.example.streamingamazing.route

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.streamingamazing.model.UserModel
import com.example.streamingamazing.screens.SigIn.SigInScreen
import com.example.streamingamazing.screens.home.HomeScreen
import com.example.streamingamazing.screens.live.LiveScreen
import com.example.streamingamazing.screens.profile.ProfileScreen
import com.example.streamingamazing.utility.BottomBarScreen
import com.example.streamingamazing.viewmodels.UserViewModel

@SuppressLint("RememberReturnType")
@Composable
fun  NavGraphApp(navController: NavHostController, isAnonymous: Boolean) {
    NavHost(
        navController = navController,
        startDestination = if (isAnonymous) StackScreen.SigIn.name else BottomBarScreen.Home.route
    ) {
        composable(StackScreen.SigIn.name) {

            SigInScreen(navController)
        }
        composable(BottomBarScreen.Home.route) {
            HomeScreen()
        }
        composable(BottomBarScreen.Live.route) {
            LiveScreen()
        }
        composable(BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
    }
}
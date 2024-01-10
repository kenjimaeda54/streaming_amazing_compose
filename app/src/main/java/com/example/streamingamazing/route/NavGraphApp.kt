package com.example.streamingamazing.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.streamingamazing.screens.home.HomeScreen
import com.example.streamingamazing.screens.live.LiveScreen
import com.example.streamingamazing.screens.profile.ProfileScreen
import com.example.streamingamazing.utility.BottomBarScreen

@Composable
fun NavGraphApp(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomBarScreen.Home.route) {
        composable(BottomBarScreen.Home.route) {
            HomeScreen()
        }
        composable(BottomBarScreen.Live.route) {
            LiveScreen()
        }
        composable(BottomBarScreen.Profile.route){
            ProfileScreen()
        }
    }
}
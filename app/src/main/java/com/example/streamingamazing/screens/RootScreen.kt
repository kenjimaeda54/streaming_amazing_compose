package com.example.streamingamazing.screens

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.streamingamazing.route.BottomCustomNavigation
import com.example.streamingamazing.route.NavGraphApp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  RootScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(bottomBar = {
        if (currentDestination != null) {
            BottomCustomNavigation(
                navHostController = navController,
                navDestination = currentDestination
            )
        }
    }) {
        NavGraphApp(navController = navController)

    }

}
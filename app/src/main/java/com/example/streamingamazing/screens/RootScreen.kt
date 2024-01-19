package com.example.streamingamazing.screens

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.streamingamazing.route.BottomCustomNavigation
import com.example.streamingamazing.route.NavGraphApp
import com.example.streamingamazing.route.StackScreen
import com.example.streamingamazing.utility.BottomScreens
import com.example.streamingamazing.view.ComposableLifecycle
import com.example.streamingamazing.viewmodels.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen() {
    val context = LocalContext.current
    val userViewModel: UserViewModel = viewModel()
    val isAnonymous by userViewModel.isAnonymous.collectAsState()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute =
        navBackStackEntry?.destination?.route?.split("/")
    val stringRoutesStack = StackScreen.values().map { it.toString() }
    val stringBottomRoute = BottomScreens.screens().map { it.route }



    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_CREATE) {
            userViewModel.getUserLogged(context)
        }

    }

    Scaffold(bottomBar = {
        if (currentDestination != null && stringBottomRoute.contains(currentRoute?.get(0))  ) {
            BottomCustomNavigation(
                navHostController = navController,
                navDestination = currentDestination
            )
        }
    }) {
        NavGraphApp(navController = navController, isAnonymous = isAnonymous)
    }

}
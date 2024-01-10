package com.example.streamingamazing.utility

import com.example.streamingamazing.R

sealed class BottomBarScreen(val route: String,val icon: Int){

    object  Home: BottomBarScreen(
        route = "home",
        icon = R.drawable.home
    )

    object  Live: BottomBarScreen(
        route = "live",
        icon = R.drawable.live
    )

    object  Profile: BottomBarScreen(
        route = "profile",
        icon = R.drawable.profile
    )


}

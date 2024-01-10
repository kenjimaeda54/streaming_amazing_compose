package com.example.streamingamazing.utility

 class BottomScreens {
     companion object {
         fun  screens(): List<BottomBarScreen> = listOf(
             BottomBarScreen.Home,
             BottomBarScreen.Live,
             BottomBarScreen.Profile
         )
     }
}
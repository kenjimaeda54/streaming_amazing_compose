package com.example.streamingamazing.route

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.streamingamazing.utility.BottomBarScreen
import com.example.streamingamazing.utility.BottomScreens


@Composable
fun BottomCustomNavigation(navHostController: NavHostController,navDestination: NavDestination) {
    BottomNavigation(modifier = Modifier
        .padding(horizontal = 35.dp)
        .offset(y = (-45).dp)
        .clip(
            CircleShape
        ),backgroundColor = MaterialTheme.colorScheme.secondary) {

        BottomScreens.screens().forEach {
            AddItem(
                navController = navHostController,
                screen = it,
                currentDestination = navDestination
            )
        }

    }

}

@Composable
fun RowScope.AddItem(
    navController: NavHostController,
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
) {

    BottomNavigationItem(
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }

                launchSingleTop = true
                restoreState = true
            }

        },
        icon = {
            if(currentDestination?.hierarchy?.any {it.route == screen.route} == true){
                Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.primary, shape = CircleShape).padding(15.dp)) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = screen.icon),
                        contentDescription = "Icon Navigation",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }

            }else {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = screen.icon),
                    contentDescription = "Icon Navigation",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        unselectedContentColor =  MaterialTheme.colorScheme.secondary.copy(0.3f),
        selectedContentColor = MaterialTheme.colorScheme.secondary,
    )

}



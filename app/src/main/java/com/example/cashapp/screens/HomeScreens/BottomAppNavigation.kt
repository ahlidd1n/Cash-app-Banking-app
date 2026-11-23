package com.example.cashapp.screens.HomeScreens

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cashapp.R
import com.example.cashapp.navigation.Cards
import com.example.cashapp.navigation.History
import com.example.cashapp.navigation.Homes
import com.example.cashapp.navigation.Pay
import com.example.cashapp.navigation.Search

data class BottomNavigationData(
    val title: String,
    val icon: Int,
    val navigation: Any,
    val selected: Boolean = false
)

val BottomNavigationList = listOf(
    BottomNavigationData(
        title = "Home",
        icon = R.drawable.homeicon,
        navigation = Homes
    ),
    BottomNavigationData(
        title = "Cards",
        icon = R.drawable.cardicon,
        navigation = Cards
    ),
    BottomNavigationData(
        title = "Pay",
        icon = R.drawable.payicon,
        navigation = Pay
    ),
    BottomNavigationData(
        title = "Search",
        icon = R.drawable.searchicon,
        navigation = Search
    ),
    BottomNavigationData(
        title = "History",
        icon = R.drawable.historyicon,
        navigation = History
    )
)


@Composable
fun BottomNavigation(navController: NavHostController) {
    NavigationBar {

        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val currentDestination = navBackStackEntry?.destination
        BottomNavigationList.forEach { screen ->

            val isSelected = currentDestination
                ?.hasRoute(screen.navigation::class) == true

            NavigationBarItem(


                    selected = navController.currentDestination
                    ?.hasRoute(screen.navigation::class) == true,

                onClick = {
                    navController.navigate(screen.navigation) {
                        popUpTo(
                            navController.graph.startDestinationId
                        ) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },

                icon = {
                    Icon(
                        painter = painterResource(screen.icon),
                        contentDescription = screen.title,
                        tint = if (isSelected) Color.Black else Color.LightGray
                    )
                },

                colors = NavigationBarItemDefaults.colors(

                )
            )
        }
    }
}
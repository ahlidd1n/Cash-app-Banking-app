package com.example.cashapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cashapp.navigation.AppNavGraph
import com.example.cashapp.repository.UserRepository
import com.example.cashapp.screens.HomeScreens.BottomNavigation
import com.example.cashapp.ui.theme.CashAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CashAppTheme {
                val navController = rememberNavController()
                val repository = remember { UserRepository(applicationContext) }
                val onBoardingState by repository.onBoardingFinished.collectAsState(initial = null)
                val registeredState by repository.isRegistered.collectAsState(initial = null)
                val finished = onBoardingState
                val registered = registeredState
                if (finished != null && registered != null) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    val showBottomBar = currentRoute?.let { route ->
                        route.contains("Home") ||
                                route.contains("Cards") ||
                                route.contains("Pay") ||
                                route.contains("Search") ||
                                route.contains("History")
                    } ?: false

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            if (showBottomBar) {
                                BottomNavigation(navController)
                            }
                        }
                    ) { innerPadding ->
                        Column(
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            AppNavGraph(
                                navController = navController,
                                isOnBoardingFinished = finished,
                                isRegistered = registered
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xff00D632))
                    )
                }
            }
        }
    }
}
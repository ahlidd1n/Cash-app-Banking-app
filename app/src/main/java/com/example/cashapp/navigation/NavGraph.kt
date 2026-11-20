package com.example.cashapp.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cashapp.repository.UserRepository
import com.example.cashapp.screens.CardScreens.MainCardScreen
import com.example.cashapp.screens.HistoryScreens.MainHistoryScreen
import com.example.cashapp.screens.HomeScreens.HomeScreen
import com.example.cashapp.screens.PayScreens.MainPayScreen
import com.example.cashapp.screens.SearchScreens.MainSearchScreen
import com.example.cashapp.screens.onboarding.OnboardingScreen
import com.example.cashapp.screens.registration.CreateAccountScreen
import com.example.cashapp.screens.registration.LoginScreen
import com.example.cashapp.viewmodel.OnBoardingViewModel
import com.example.cashapp.viewmodel.OnBoardingViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Composable
fun AppNavGraph(
    navController: NavHostController,
    isOnBoardingFinished: Boolean,
    isRegistered: Boolean
) {
    val startDestination: Any = when {
        isRegistered -> Home
        isOnBoardingFinished -> SingUp
        else -> OnBoardingRout
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repository = remember { UserRepository(context.applicationContext) }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<OnBoardingRout> {
            val context: Context = LocalContext.current
            val onBoardingViewModel: OnBoardingViewModel = viewModel(
                factory = OnBoardingViewModelFactory(UserRepository(context.applicationContext))
            )
            OnboardingScreen(
                viewModel = onBoardingViewModel,
                onFinished = {
                    navController.navigate(SingUp) {
                        popUpTo<OnBoardingRout> { inclusive = true }
                    }
                }
            )
        }

        composable<SingUp> {
            CreateAccountScreen(
                onLogInClick = {
                    navController.navigate(SingIn)
                },
                onFinish = {
                    scope.launch {
                        repository.setRegistered(true)
                        navController.navigate(route = Home) {
                            popUpTo<SingUp> { inclusive = true }
                        }
                    }
                }
            )
        }

        composable<SingIn> {
            LoginScreen(
                onSignUpClick = {
                    navController.navigate(SingUp)
                },
                onLogInClick = {
                    scope.launch {
                        repository.setRegistered(true)
                        navController.navigate(route = Home) {
                            popUpTo<SingUp> { inclusive = true }
                        }
                    }
                }
            )
        }

        composable<Home> {
            HomeScreen(
                navigation = {
                    navController.navigate(Home)
                }
            )
        }

        composable<Cards> {
            MainCardScreen(
                navigation = {
                    navController.navigate(Cards)
                }
            )
        }

        composable<Pay> {
            MainPayScreen(
                navigation = {
                    navController.navigate(Pay)
                }
            )
        }

        composable<Search> {
            MainSearchScreen(
                navigation = {
                    navController.navigate(Search)
                }
            )
        }

        composable<History> {
            MainHistoryScreen(
                navigation = {
                    navController.navigate(History)
                }
            )
        }
    }
}

@Serializable
object OnBoardingRout

@Serializable
object SingUp

@Serializable
object SingIn

@Serializable
object Home

@Serializable
object Cards

@Serializable
object Pay

@Serializable
object Search

@Serializable
object History
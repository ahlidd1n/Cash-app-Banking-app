package com.example.cashapp.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cashapp.screens.CardScreens.MainCardScreen
import com.example.cashapp.screens.HistoryScreens.MainHistoryScreen
import com.example.cashapp.screens.HomeScreens.HomeScreen
import com.example.cashapp.screens.PayScreens.MainPayScreen
import com.example.cashapp.screens.SearchScreens.MainSearchScreen
import com.example.cashapp.screens.onboarding.OnboardingScreen
import com.example.cashapp.screens.registration.CreateAccountScreen
import com.example.cashapp.screens.registration.LoginScreen
import kotlinx.serialization.Serializable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.cashapp.repository.UserRepository
import com.example.cashapp.screens.HomeScreens.AddCash
import com.example.cashapp.viewmodel.OnBoardingViewModel
import com.example.cashapp.viewmodel.OnBoardingViewModelFactory
import com.example.cashapp.viewmodel.UserViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    isOnBoardingFinished: Boolean,
    isRegistered: Boolean
) {
    val context = LocalContext.current
    val userRepository = remember { UserRepository(context.applicationContext) }
    val isLogin by userRepository.isLogin.collectAsStateWithLifecycle(initialValue = null)

    val loggedIn = isLogin ?: return

    val startDestination: Any = when {
        loggedIn -> Homes
        isRegistered -> SingIn
        isOnBoardingFinished -> SingUp
        else -> OnBoardingRout
    }

    val userViewModel: UserViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return UserViewModel(userRepository) as T
            }
        }
    )

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<OnBoardingRout> {
            val onBoardingViewModel: OnBoardingViewModel = viewModel(
                factory = OnBoardingViewModelFactory(userRepository)
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
                viewModel = userViewModel,
                onLogInClick = { navController.navigate(route = SingIn) },
                onAccountCreated = {
                    navController.navigate(route = SingIn) {
                        popUpTo<SingUp> { inclusive = true }
                    }
                }
            )
        }

        composable<SingIn> {
            LoginScreen(
                viewModel = userViewModel,
                onSignUpClick = { navController.navigate(SingUp) },
                onLogInSuccess = {
                    navController.navigate(Homes) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable<Homes> {
            LaunchedEffect(Unit) {
                userViewModel.loadCurrentUser()
            }
            HomeScreen(
                viewModel = userViewModel,
                navigation = {
                    navController.navigate(Homes)
                },
                navigateToAddCashScreen = {
                    navController.navigate(AddCashScreen)
                }
            )
        }

        composable <AddCashScreen>{
            AddCash()
        }

        composable<Cards> {
            MainCardScreen(navigation = { navController.navigate(Cards) })
        }

        composable<Pay> {
            MainPayScreen(navigation = { navController.navigate(Pay) })
        }

        composable<Search> {
            MainSearchScreen(navigation = { navController.navigate(Search) })
        }

        composable<History> {
            MainHistoryScreen(navigation = { navController.navigate(History) })
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
object Homes

@Serializable
object Cards

@Serializable
object Pay

@Serializable
object Search

@Serializable
object History
@Serializable
object AddCashScreen
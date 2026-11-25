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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.cashapp.repository.UserRepository
import com.example.cashapp.screens.HomeScreens.AddCash
import com.example.cashapp.screens.ProfileScreen.InviteFriendsScreen
import com.example.cashapp.screens.ProfileScreen.SettingScreens.DocumentsScreen
import com.example.cashapp.screens.ProfileScreen.SettingScreens.FamilyScreen
import com.example.cashapp.screens.ProfileScreen.SettingScreens.FavoritesScreen
import com.example.cashapp.screens.ProfileScreen.SettingScreens.LanguageScreen
import com.example.cashapp.screens.ProfileScreen.SettingScreens.LimitsScreen
import com.example.cashapp.screens.ProfileScreen.SettingScreens.LinkedBanksScreen
import com.example.cashapp.screens.ProfileScreen.SettingScreens.NotificationsScreen
import com.example.cashapp.screens.ProfileScreen.SettingScreens.PersonalScreen
import com.example.cashapp.screens.ProfileScreen.SettingScreens.SecurityPrivacyScreen
import com.example.cashapp.screens.ProfileScreen.SettingScreens.SupportScreen
import com.example.cashapp.screens.ProfileScreen.YourAccountScreen
import com.example.cashapp.screens.SavingsScreen.SavingsScreen
import com.example.cashapp.viewmodel.OnBoardingViewModel
import com.example.cashapp.viewmodel.OnBoardingViewModelFactory
import com.example.cashapp.viewmodel.UiState
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
                },
                navigateToSavingScreen = {
                    navController.navigate(SavingScreen)
                },
                navigateToProfileScreen = {
                    navController.navigate(ProfileScreen)
                }
            )
        }



        composable<SavingScreen> {
            SavingsScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                viewModel = userViewModel
            )
        }

        composable <AddCashScreen>{
            AddCash()
        }




        composable<Cards> {
            MainCardScreen(
                navigation = {
                navController.navigate(Cards)
            },
                viewModel = userViewModel
                )
        }

        composable<Pay> {
            LaunchedEffect(Unit) {
                if (userViewModel.uiState.value is UiState.Loading) {
                    userViewModel.loadCurrentUser()
                }
            }
            MainPayScreen(
                viewModel = userViewModel,
                navigation = {navController.navigate(Pay)},
                onAddMoneyClick = { navController.navigate(AddCashScreen) },
                onSaveClick = { navController.navigate(SavingScreen) },
                onCardClick = { navController.navigate(Cards) }
            )
        }

        composable<Search> {
            MainSearchScreen(navigation = { navController.navigate(Search) })
        }

        composable<History> {
            MainHistoryScreen(navigation = { navController.navigate(History) })
        }

        composable <ProfileScreen>{
            LaunchedEffect(Unit) {
                if (userViewModel.uiState.value is UiState.Loading) {
                    userViewModel.loadCurrentUser()
                }
            }
            YourAccountScreen(
                viewModel = userViewModel,
                onCloseClick = {navController.popBackStack()},
                onInviteFriendsClick = {navController.navigate(InviteFriendsScreen)},
                goToSupport = {navController.navigate(SupportScreen)},
                goToDocuments = {navController.navigate(DocumentsScreen)},
                goToLimits = {navController.navigate(LimitsScreen)},
                goToFamily = {navController.navigate(FamilyScreen)},
                goTOSecurity = {navController.navigate(SecurityPrivacyScreen)},
                goTOLinkedBanks = {navController.navigate(LinkedBanksScreen)},
                goToFavorites = {navController.navigate(FavoritesScreen)},
                goToNotification = {navController.navigate(NotificationScreen)},
                goToPersonal = {navController.navigate(PersonalScreen)},
                goToLanguage = {navController.navigate(LanguageScreen)}
            )
        }


        //Setting Screens
        composable <SupportScreen>{
            SupportScreen(onBackClick = { navController.popBackStack() })
        }

        composable<DocumentsScreen> {
            DocumentsScreen(onBackClick = { navController.popBackStack() })
        }

        composable<LimitsScreen>{
            LimitsScreen(
                viewModel = userViewModel,
                onBackClick = { navController.popBackStack() })
        }
        composable <FamilyScreen>{
            FamilyScreen(onBackClick = { navController.popBackStack() })
        }
        composable <SecurityPrivacyScreen>{
            SecurityPrivacyScreen(onBackClick = { navController.popBackStack() })
        }
        composable <LinkedBanksScreen>{
            LinkedBanksScreen(onBackClick = {navController.popBackStack()})
        }

        composable<FavoritesScreen> {
            FavoritesScreen(
                viewModel = userViewModel,
                onBackClick = { navController.popBackStack() },
                onNavigateToSavings = { navController.navigate(SavingScreen) },
                onNavigateToCard = { navController.navigate(Cards) },
                onNavigateToLimits = { navController.navigate(LimitsScreen) }
            )
        }

        composable<NotificationScreen> {
            NotificationsScreen(
                viewModel = userViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<PersonalScreen> {
            PersonalScreen(
                viewModel = userViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<LanguageScreen> {
            LanguageScreen(onBackClick = { navController.popBackStack() })
        }

        composable<InviteFriendsScreen> {
            InviteFriendsScreen(
                viewModel = userViewModel,
                onBackClick = { navController.popBackStack() }
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
@Serializable
object SavingScreen
@Serializable
object ProfileScreen


//Setting Screens
@Serializable
object SupportScreen
@Serializable
object DocumentsScreen

@Serializable
object LimitsScreen
@Serializable
object FamilyScreen
@Serializable
object SecurityPrivacyScreen
@Serializable
object LinkedBanksScreen
@Serializable
object FavoritesScreen
@Serializable
object NotificationScreen
@Serializable
object PersonalScreen
@Serializable
object LanguageScreen
@Serializable
object InviteFriendsScreen
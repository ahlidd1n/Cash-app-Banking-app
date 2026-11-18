package com.example.cashapp.navigation

import android.app.PendingIntent
import android.content.pm.SigningInfo
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cashapp.screens.onboarding.OnboardingScreen
import com.example.cashapp.screens.registration.CreateAccountScreen
import com.example.cashapp.screens.registration.LoginScreen
import kotlinx.serialization.Serializable

@Composable
fun AppNavGraph(){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = OnBoardingRout
    ){

        composable<OnBoardingRout> {
            OnboardingScreen(
                onFinished = {
                    // Инҷо ба экрани SingUp мегузарем ва Onboarding-ро аз стек тоза мекунем
                    navController.navigate(SingUp) {
                        popUpTo<OnBoardingRout> { inclusive = true }
                    }
                }
            )
        }

        composable <SingUp>{
            CreateAccountScreen(
                onLogInClick = {
                    navController.navigate(SingIn)
                }
            )
        }

        composable <SingIn>{
            LoginScreen(
                onSignUpClick = {
                    navController.navigate(SingUp)
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
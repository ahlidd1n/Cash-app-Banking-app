package com.example.cashapp.screens.HomeScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cashapp.viewmodel.UiState
import com.example.cashapp.viewmodel.UserViewModel


@Composable
fun HomeScreen(
    navigation: () -> Unit = {},
    viewModel: UserViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        when (val user = state) {

            is UiState.Loading -> {
                    CircularProgressIndicator()
            }

            is UiState.Success -> {
                Text("Welcome, ${user.userInfo.fullName}")
                Text("Email: ${user.userInfo.email}")
                Text("Phone: ${user.userInfo.phone}")
            }

            is UiState.Error -> {
                Text("Error: ${user.errorMassage}")
            }
        }
    }
}

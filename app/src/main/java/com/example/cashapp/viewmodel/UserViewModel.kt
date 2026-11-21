package com.example.cashapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cashapp.NetWork.RetrofitClient
import com.example.cashapp.data.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState{
    object Loading : UiState()
    data class Success(val userInfo: List<UserInfo>): UiState()
    data class Error(val errorMassage: String): UiState()
}

class UserViewModel: ViewModel(){
    val _uistate = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> = _uistate.asStateFlow()

    init {
        loadUser()
    }

    fun loadUser(){
        viewModelScope.launch {
            _uistate.value = UiState.Loading
            try {
                val user = RetrofitClient.api.loadUser()
                _uistate.value = UiState.Success(user)
            }catch (e: Exception){
                _uistate.value = UiState.Error("$e")
            }
        }
    }
}
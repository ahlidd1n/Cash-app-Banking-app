package com.example.cashapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cashapp.NetWork.RetrofitClient
import com.example.cashapp.data.NewUser
import com.example.cashapp.data.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState{
    object Loading : UiState()
    data class Success(val userInfo: UserInfo): UiState()
    data class Error(val errorMassage: String): UiState()
}



class UserViewModel : ViewModel() {
    private val _uiState =
        MutableStateFlow<UiState>(UiState.Loading)

    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()

    val emailOrPhone = ""
    val password = ""
    fun login(emailOrPhone: String, password: String) {

        viewModelScope.launch {

            _uiState.value = UiState.Loading

            try {
                val users = RetrofitClient.api.loadUser()

                val user = users.firstOrNull { user ->

                    _uiState.value = UiState.Success(user)
                    (user.email.equals(emailOrPhone, ignoreCase = true) ||
                            user.phone == emailOrPhone) &&
                            user.password == password

                }



            } catch (e: Exception) {

                _uiState.value = UiState.Error(
                    errorMassage = e.message ?: "Something went wrong!",
                )
            }
        }
    }


    init {
        login(emailOrPhone, password)
    }
    val _saveUi = MutableStateFlow<SaveUi>(SaveUi.Saving)

    fun saveUser(user: NewUser) {
        viewModelScope.launch {
            _saveUi.value = SaveUi.Saving
            _uiState.value = UiState.Loading
            try {
                val savedUser = RetrofitClient.api.saveUser(user)
                _saveUi.value = SaveUi.Success(savedUser)
                _uiState.value = UiState.Success(savedUser)
            } catch (e: Exception) {
                val msg = e.message ?: "Something went wrong!"
                _saveUi.value = SaveUi.Error(msg)
                _uiState.value = UiState.Error(msg)
            }
        }
    }
}



sealed class SaveUi{
    object Saving : SaveUi()
    data class Success(val user: UserInfo): SaveUi()
    data class Error(val massage: String): SaveUi()
}



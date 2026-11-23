package com.example.cashapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cashapp.NetWork.RetrofitClient
import com.example.cashapp.data.NewUser
import com.example.cashapp.data.UserInfo
import com.example.cashapp.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    object Loading : UiState()
    data class Success(val userInfo: UserInfo) : UiState()
    data class Error(val errorMassage: String) : UiState()
}

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _loginUi = MutableStateFlow<LoginUi>(LoginUi.Idle)
    val loginUi: StateFlow<LoginUi> = _loginUi.asStateFlow()

    private val _saveUi = MutableStateFlow<SaveUi>(SaveUi.Idle)
    val saveUi: StateFlow<SaveUi> = _saveUi.asStateFlow()

    fun saveUser(user: NewUser) {
        viewModelScope.launch {
            _saveUi.value = SaveUi.Saving
            try {
                val savedUser = RetrofitClient.api.saveUser(user)
                userRepository.setRegistered(true)
                _saveUi.value = SaveUi.Success(savedUser)
            } catch (e: Exception) {
                _saveUi.value = SaveUi.Error(e.message ?: "Something went wrong!")
            }
        }
    }

    fun loginUser(emailOrPhone: String, password: String) {
        viewModelScope.launch {
            _loginUi.value = LoginUi.Loading
            _uiState.value = UiState.Loading
            try {
                val users = RetrofitClient.api.loadUser()
                val cleanInput = emailOrPhone.trim()

                val matchedUser = users.firstOrNull { user ->
                    (user.email.equals(cleanInput, ignoreCase = true) ||
                            user.phone == cleanInput) &&
                            user.password == password
                }

                if (matchedUser != null) {
                    userRepository.saveUserIdentifier(matchedUser.email.ifBlank { matchedUser.phone })
                    userRepository.setLogin(true)
                    _uiState.value = UiState.Success(matchedUser)
                    _loginUi.value = LoginUi.Success
                } else {
                    val msg = "Invalid email/phone or password!"
                    _uiState.value = UiState.Error(msg)
                    _loginUi.value = LoginUi.Error(msg)
                }
            } catch (e: Exception) {
                val msg = e.message ?: "Something went wrong!"
                _uiState.value = UiState.Error(msg)
                _loginUi.value = LoginUi.Error(msg)
            }
        }
    }

    fun loadCurrentUser() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val users = RetrofitClient.api.loadUser()
                val savedIdentifier = userRepository.getUserIdentifier()
                val currentUser = users.firstOrNull {
                    it.email.equals(savedIdentifier, ignoreCase = true) || it.phone == savedIdentifier
                } ?: users.firstOrNull()
                if (currentUser != null) {
                    _uiState.value = UiState.Success(currentUser)
                } else {
                    _uiState.value = UiState.Error("User list is empty")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Something went wrong!")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.setLogin(false)
        }
    }

    fun resetSaveState() {
        _saveUi.value = SaveUi.Idle
    }

    fun resetLoginState() {
        _loginUi.value = LoginUi.Idle
    }
}
sealed class SaveUi {
    object Idle : SaveUi()
    object Saving : SaveUi()
    data class Success(val user: UserInfo) : SaveUi()
    data class Error(val massage: String) : SaveUi()
}


sealed class LoginUi {
    object Idle : LoginUi()
    object Loading : LoginUi()
    object Success : LoginUi()
    data class Error(val message: String) : LoginUi()
}
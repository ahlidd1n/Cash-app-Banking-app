package com.example.cashapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cashapp.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OnBoardingViewModel(private val repository: UserRepository) : ViewModel() {

    val onBoardingState: StateFlow<Boolean> = repository.onBoardingFinished
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun onBoardingFinish() {
        viewModelScope.launch {
            repository.setOnBoardingFinished(true)
        }
    }
}

class OnBoardingViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OnBoardingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OnBoardingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
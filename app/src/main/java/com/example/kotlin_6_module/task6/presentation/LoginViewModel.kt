package com.example.kotlin_6_module.task6.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_6_module.task6.domain.usecase.LoginUseCase
import com.example.kotlin_6_module.task6.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val token: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _state.value = LoginState.Loading
            val result = loginUseCase(username, password)
            result.fold(
                onSuccess = { token ->
                    authRepository.saveToken(token)
                    _state.value = LoginState.Success(token)
                },
                onFailure = {
                    _state.value = LoginState.Error("Неверный логин или пароль")
                }
            )
        }
    }
}
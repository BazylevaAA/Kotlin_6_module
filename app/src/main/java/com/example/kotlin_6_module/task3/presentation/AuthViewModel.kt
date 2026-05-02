package com.example.kotlin_6_module.task3.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kotlin_6_module.task3.data.AuthRepositoryImpl
import com.example.kotlin_6_module.task3.data.TokenDataStore
import com.example.kotlin_6_module.task3.domain.GetUserDetailUseCase
import com.example.kotlin_6_module.task3.domain.GetUsersUseCase
import com.example.kotlin_6_module.task3.domain.LoginUseCase
import com.example.kotlin_6_module.task3.domain.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val token: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

sealed class UsersState {
    object Loading : UsersState()
    data class Success(val users: List<User>) : UsersState()
    data class Error(val message: String) : UsersState()
}

sealed class UserDetailState {
    object Loading : UserDetailState()
    data class Success(val user: User) : UserDetailState()
    data class Error(val message: String) : UserDetailState()
}

class AuthViewModel(context: Context) : ViewModel() {

    private val tokenDataStore = TokenDataStore(context)
    private val repository = AuthRepositoryImpl(tokenDataStore)
    private val loginUseCase = LoginUseCase(repository)
    private val getUsersUseCase = GetUsersUseCase(repository)
    private val getUserDetailUseCase = GetUserDetailUseCase(repository)

    val authState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Idle)
    val usersState: MutableStateFlow<UsersState> = MutableStateFlow(UsersState.Loading)
    val userDetailState: MutableStateFlow<UserDetailState> = MutableStateFlow(UserDetailState.Loading)
    val token: MutableStateFlow<String?> = MutableStateFlow(null)

    init {
        viewModelScope.launch {
            val savedToken = repository.getToken()
            token.value = savedToken
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            authState.value = AuthState.Loading
            try {
                val t = loginUseCase(username, password)
                token.value = t
                authState.value = AuthState.Success(t)
            } catch (e: Exception) {
                authState.value = AuthState.Error(e.message ?: "Ошибка входа")
            }
        }
    }

    fun loadUsers() {
        viewModelScope.launch {
            usersState.value = UsersState.Loading
            try {
                val t = token.value ?: repository.getToken() ?: ""
                val users = getUsersUseCase(t)
                usersState.value = UsersState.Success(users)
            } catch (e: Exception) {
                usersState.value = UsersState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    fun loadUserDetail(id: Int) {
        viewModelScope.launch {
            userDetailState.value = UserDetailState.Loading
            try {
                val t = token.value ?: repository.getToken() ?: ""
                val user = getUserDetailUseCase(id, t)
                userDetailState.value = UserDetailState.Success(user)
            } catch (e: Exception) {
                userDetailState.value = UserDetailState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.clearToken()
            token.value = null
            authState.value = AuthState.Idle
        }
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(context) as T
        }
    }
}
package com.example.kotlin_6_module.task6.data.repository

import com.example.kotlin_6_module.task6.data.remote.NobelApiClient
import com.example.kotlin_6_module.task6.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val apiClient: NobelApiClient,
    private val tokenStorage: TokenStorage
) : AuthRepository {

    override suspend fun login(username: String, password: String): Result<String> {
        return try {
            val response = apiClient.login(username, password)
            Result.success(response.token)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveToken(token: String) {
        tokenStorage.saveToken(token)
    }

    override suspend fun getToken(): String? {
        return tokenStorage.getToken()
    }

    override suspend fun clearToken() {
        tokenStorage.clearToken()
    }
}
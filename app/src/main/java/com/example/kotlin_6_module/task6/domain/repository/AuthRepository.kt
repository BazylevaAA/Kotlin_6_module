package com.example.kotlin_6_module.task6.domain.repository

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<String>
    suspend fun saveToken(token: String)
    suspend fun getToken(): String?
    suspend fun clearToken()
}
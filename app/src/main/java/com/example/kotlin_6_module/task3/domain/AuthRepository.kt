package com.example.kotlin_6_module.task3.domain

interface AuthRepository {
    suspend fun login(username: String, password: String): String
    suspend fun getUsers(token: String): List<User>
    suspend fun getUserById(id: Int, token: String): User
    suspend fun saveToken(token: String)
    suspend fun getToken(): String?
    suspend fun clearToken()
}
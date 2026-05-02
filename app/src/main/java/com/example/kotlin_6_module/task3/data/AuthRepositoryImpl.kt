package com.example.kotlin_6_module.task3.data

import com.example.kotlin_6_module.task3.domain.AuthRepository
import com.example.kotlin_6_module.task3.domain.User
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class AuthRepositoryImpl(
    private val tokenDataStore: TokenDataStore
) : AuthRepository {

    private val baseUrl = "https://dummyjson.com"

    override suspend fun login(username: String, password: String): String {
        val response = authKtorClient.post("$baseUrl/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(username, password))
        }
        return response.body<LoginResponse>().accessToken
    }

    override suspend fun getUsers(token: String): List<User> {
        val response = authKtorClient.get("$baseUrl/users") {
            header("Authorization", "Bearer $token")
        }
        return response.body<UsersResponse>().users.map {
            User(it.id, it.firstName, it.lastName, it.username, it.email, it.image)
        }
    }

    override suspend fun getUserById(id: Int, token: String): User {
        val response = authKtorClient.get("$baseUrl/users/$id") {
            header("Authorization", "Bearer $token")
        }
        val dto = response.body<UserDto>()
        return User(dto.id, dto.firstName, dto.lastName, dto.username, dto.email, dto.image)
    }

    override suspend fun saveToken(token: String) = tokenDataStore.saveToken(token)
    override suspend fun getToken(): String? = tokenDataStore.getToken()
    override suspend fun clearToken() = tokenDataStore.clearToken()
}
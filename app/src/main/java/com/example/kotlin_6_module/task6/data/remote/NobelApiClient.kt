package com.example.kotlin_6_module.task6.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class NobelApiClient {

    private val BASE_URL = "http://10.0.2.2:8080"

    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.BODY
        }
    }

    suspend fun login(username: String, password: String): LoginResponseDto {
        return client.post("$BASE_URL/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequestDto(username, password))
        }.body()
    }

    suspend fun getPrizes(token: String, year: String? = null, category: String? = null): List<NobelPrizeDto> {
        return client.get("$BASE_URL/prizes") {
            bearerAuth(token)
            year?.let { parameter("year", it) }
            category?.let { parameter("category", it) }
        }.body()
    }

    suspend fun getLaureates(token: String, year: String, category: String): List<LaureateDtoItem> {
        return client.get("$BASE_URL/prizes/$year/$category/laureates") {
            bearerAuth(token)
        }.body()
    }

    suspend fun getMe(token: String): UserDto {
        return client.get("$BASE_URL/users/me") {
            bearerAuth(token)
        }.body()
    }

    suspend fun getFavoritePrizes(token: String): List<NobelPrizeDto> {
        return client.get("$BASE_URL/users/me/prizes") {
            bearerAuth(token)
        }.body()
    }

    suspend fun addFavorite(token: String, prizeId: Int) {
        client.post("$BASE_URL/users/me/prizes/$prizeId") {
            bearerAuth(token)
        }
    }

    suspend fun removeFavorite(token: String, prizeId: Int) {
        client.delete("$BASE_URL/users/me/prizes/$prizeId") {
            bearerAuth(token)
        }
    }
}
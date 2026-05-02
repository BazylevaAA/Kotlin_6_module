package com.example.kotlin_6_module.task2.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class NobelPrizeApi(private val client: HttpClient) {

    suspend fun getPrizes(year: String?, category: String?): NobelPrizesResponse {
        return client.get("https://api.nobelprize.org/2.1/nobelPrizes") {
            parameter("limit", 25)
            parameter("offset", 0)
            if (!year.isNullOrBlank()) parameter("nobelPrizeYear", year)
            if (!category.isNullOrBlank()) parameter("nobelPrizeCategory", category)
        }.body()
    }
}
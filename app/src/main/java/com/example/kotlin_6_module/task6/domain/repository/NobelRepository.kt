package com.example.kotlin_6_module.task6.domain.repository

import com.example.kotlin_6_module.task6.domain.model.Laureate
import com.example.kotlin_6_module.task6.domain.model.NobelPrize
import com.example.kotlin_6_module.task6.domain.model.User

interface NobelRepository {
    suspend fun getPrizes(token: String, year: String? = null, category: String? = null): Result<List<NobelPrize>>
    suspend fun getLaureates(token: String, year: String, category: String): Result<List<Laureate>>
    suspend fun getMe(token: String): Result<User>
    suspend fun getFavoritePrizes(token: String): Result<List<NobelPrize>>
    suspend fun addFavorite(token: String, prizeId: Int): Result<Unit>
    suspend fun removeFavorite(token: String, prizeId: Int): Result<Unit>
}
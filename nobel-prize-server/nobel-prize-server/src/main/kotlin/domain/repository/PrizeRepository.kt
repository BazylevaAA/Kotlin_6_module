package com.example.domain.repository

import com.example.domain.model.Laureate
import com.example.domain.model.NobelPrize

interface PrizeRepository {
    suspend fun getAllPrizes(year: Int? = null, category: String? = null): List<NobelPrize>
    suspend fun getPrize(year: Int, category: String): NobelPrize?
    suspend fun getLaureates(year: Int, category: String): List<Laureate>
    suspend fun getFavoritePrizes(userId: Int): List<NobelPrize>
    suspend fun addFavoritePrize(userId: Int, prizeId: Int): Boolean
    suspend fun removeFavoritePrize(userId: Int, prizeId: Int): Boolean
}
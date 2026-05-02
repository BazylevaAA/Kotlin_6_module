package com.example.kotlin_6_module.domain

interface NobelPrizeRepository {
    suspend fun getPrizes(year: String?, category: String?): List<NobelPrize>
}
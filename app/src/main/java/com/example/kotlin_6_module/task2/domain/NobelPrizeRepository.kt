package com.example.kotlin_6_module.task2.domain

interface NobelPrizeRepository {
    suspend fun getPrizes(year: String?, category: String?): List<NobelPrize>
}
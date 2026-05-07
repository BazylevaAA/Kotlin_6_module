package com.example.domain.repository

import com.example.domain.model.NobelPrize

interface NobelPrizeRepository {
    fun getAll(): List<NobelPrize>
    fun getByYearAndCategory(year: String, category: String): NobelPrize?
}
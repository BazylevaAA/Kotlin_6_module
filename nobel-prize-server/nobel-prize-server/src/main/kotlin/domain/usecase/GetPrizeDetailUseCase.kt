package com.example.domain.usecase

import com.example.domain.model.NobelPrize
import com.example.domain.repository.NobelPrizeRepository

class GetPrizeDetailUseCase(private val repository: NobelPrizeRepository) {
    operator fun invoke(year: String, category: String): NobelPrize? =
        repository.getByYearAndCategory(year, category)
}
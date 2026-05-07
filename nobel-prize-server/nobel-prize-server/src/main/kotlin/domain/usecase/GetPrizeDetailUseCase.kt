package com.example.domain.usecase

import com.example.domain.model.NobelPrize
import com.example.domain.repository.PrizeRepository

class GetPrizeUseCase(private val prizeRepository: PrizeRepository) {
    suspend fun execute(year: Int, category: String): NobelPrize? =
        prizeRepository.getPrize(year, category)
}
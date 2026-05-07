package com.example.domain.usecase

import com.example.domain.model.NobelPrize
import com.example.domain.repository.PrizeRepository

class GetFavoritePrizesUseCase(private val prizeRepository: PrizeRepository) {
    suspend fun execute(userId: Int): List<NobelPrize> =
        prizeRepository.getFavoritePrizes(userId)
}
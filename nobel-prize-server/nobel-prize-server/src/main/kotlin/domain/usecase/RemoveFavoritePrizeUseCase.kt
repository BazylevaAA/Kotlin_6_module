package com.example.domain.usecase

import com.example.domain.repository.PrizeRepository

class RemoveFavoritePrizeUseCase(private val prizeRepository: PrizeRepository) {
    suspend fun execute(userId: Int, prizeId: Int): Boolean =
        prizeRepository.removeFavoritePrize(userId, prizeId)
}
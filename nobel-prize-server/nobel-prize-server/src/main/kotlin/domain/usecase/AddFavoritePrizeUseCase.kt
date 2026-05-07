package com.example.domain.usecase

import com.example.domain.repository.PrizeRepository

class AddFavoritePrizeUseCase(private val prizeRepository: PrizeRepository) {
    suspend fun execute(userId: Int, prizeId: Int): Boolean =
        prizeRepository.addFavoritePrize(userId, prizeId)
}
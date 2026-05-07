package com.example.domain.usecase

import com.example.domain.model.NobelPrize
import com.example.domain.repository.PrizeRepository

class GetPrizesUseCase(private val prizeRepository: PrizeRepository) {
    suspend fun execute(): List<NobelPrize> = prizeRepository.getAllPrizes()
}
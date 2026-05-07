package com.example.domain.usecase

import com.example.domain.model.Laureate
import com.example.domain.repository.PrizeRepository

class GetLaureatesUseCase(private val prizeRepository: PrizeRepository) {
    suspend fun execute(year: Int, category: String): List<Laureate> =
        prizeRepository.getLaureates(year, category)
}
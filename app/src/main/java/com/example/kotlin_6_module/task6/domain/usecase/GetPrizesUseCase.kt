package com.example.kotlin_6_module.task6.domain.usecase

import com.example.kotlin_6_module.task6.domain.model.NobelPrize
import com.example.kotlin_6_module.task6.domain.repository.NobelRepository

class GetPrizesUseCase(private val repository: NobelRepository) {
    suspend operator fun invoke(token: String, year: String? = null, category: String? = null): Result<List<NobelPrize>> {
        return repository.getPrizes(token, year, category)
    }
}
package com.example.kotlin_6_module.task6.domain.usecase

import com.example.kotlin_6_module.task6.domain.model.NobelPrize
import com.example.kotlin_6_module.task6.domain.repository.NobelRepository

class GetFavoritesUseCase(private val repository: NobelRepository) {
    suspend operator fun invoke(token: String): Result<List<NobelPrize>> {
        return repository.getFavoritePrizes(token)
    }
}
package com.example.kotlin_6_module.task6.domain.usecase

import com.example.kotlin_6_module.task6.domain.repository.NobelRepository

class ToggleFavoriteUseCase(private val repository: NobelRepository) {
    suspend fun add(token: String, prizeId: Int): Result<Unit> {
        return repository.addFavorite(token, prizeId)
    }
    suspend fun remove(token: String, prizeId: Int): Result<Unit> {
        return repository.removeFavorite(token, prizeId)
    }
}
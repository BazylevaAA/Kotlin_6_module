package com.example.kotlin_6_module.task6.domain.usecase

import com.example.kotlin_6_module.task6.domain.model.Laureate
import com.example.kotlin_6_module.task6.domain.repository.NobelRepository

class GetLaureatesUseCase(private val repository: NobelRepository) {
    suspend operator fun invoke(token: String, year: String, category: String): Result<List<Laureate>> {
        return repository.getLaureates(token, year, category)
    }
}
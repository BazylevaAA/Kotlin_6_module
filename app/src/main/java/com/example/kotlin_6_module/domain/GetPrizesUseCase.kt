package com.example.kotlin_6_module.task2.domain

import com.example.kotlin_6_module.domain.NobelPrize
import com.example.kotlin_6_module.domain.NobelPrizeRepository

class GetPrizesUseCase(private val repository: NobelPrizeRepository) {
    suspend operator fun invoke(year: String?, category: String?): List<NobelPrize> {
        return repository.getPrizes(year, category)
    }
}
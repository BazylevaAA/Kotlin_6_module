package com.example.kotlin_6_module.task2.domain

class GetPrizesUseCase(private val repository: NobelPrizeRepository) {
    suspend operator fun invoke(year: String?, category: String?): List<NobelPrize> {
        return repository.getPrizes(year, category)
    }
}
package com.example.domain.usecase

import com.example.domain.model.NobelPrize
import com.example.domain.repository.NobelPrizeRepository

class GetAllPrizesUseCase(private val repository: NobelPrizeRepository) {
    operator fun invoke(): List<NobelPrize> = repository.getAll()
}
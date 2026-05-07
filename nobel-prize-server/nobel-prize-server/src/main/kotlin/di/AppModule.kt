package com.example.di

import com.example.data.repository.NobelPrizeRepositoryImpl
import com.example.domain.repository.NobelPrizeRepository
import com.example.domain.usecase.GetAllPrizesUseCase
import com.example.domain.usecase.GetPrizeDetailUseCase
import com.example.presentation.NobelPrizeController

object AppModule {
    private val repository: NobelPrizeRepository = NobelPrizeRepositoryImpl()

    val getAllPrizesUseCase = GetAllPrizesUseCase(repository)
    val getPrizeDetailUseCase = GetPrizeDetailUseCase(repository)

    val nobelPrizeController = NobelPrizeController(
        getAllPrizesUseCase = getAllPrizesUseCase,
        getPrizeDetailUseCase = getPrizeDetailUseCase
    )
}
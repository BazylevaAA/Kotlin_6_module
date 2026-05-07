package com.example.di

import com.example.data.repository.PrizeRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.PrizeRepository
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.*

object AppContainer {
    val userRepository: UserRepository = UserRepositoryImpl()
    val prizeRepository: PrizeRepository = PrizeRepositoryImpl()

    val loginUseCase = LoginUseCase(userRepository)
    val getPrizesUseCase = GetPrizesUseCase(prizeRepository)
    val getPrizeUseCase = GetPrizeUseCase(prizeRepository)
    val getLaureatesUseCase = GetLaureatesUseCase(prizeRepository)
    val addFavoritePrizeUseCase = AddFavoritePrizeUseCase(prizeRepository)
    val removeFavoritePrizeUseCase = RemoveFavoritePrizeUseCase(prizeRepository)
    val getFavoritePrizesUseCase = GetFavoritePrizesUseCase(prizeRepository)
}
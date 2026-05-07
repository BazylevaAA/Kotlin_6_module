package com.example.routing

import com.example.di.AppContainer
import com.example.presentation.AuthController
import com.example.presentation.PrizeController
import com.example.presentation.UserController
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val authController = AuthController(AppContainer.loginUseCase)
    val prizeController = PrizeController(
        AppContainer.getPrizesUseCase,
        AppContainer.getPrizeUseCase,
        AppContainer.getLaureatesUseCase,
        AppContainer.addFavoritePrizeUseCase,
        AppContainer.removeFavoritePrizeUseCase,
        AppContainer.getFavoritePrizesUseCase
    )
    val userController = UserController(
        AppContainer.userRepository,
        AppContainer.getFavoritePrizesUseCase,
        AppContainer.addFavoritePrizeUseCase,
        AppContainer.removeFavoritePrizeUseCase
    )

    routing {
        authController.configure(this)
        prizeController.configure(this)
        userController.configure(this)
    }
}
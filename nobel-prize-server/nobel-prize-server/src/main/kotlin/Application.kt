package com.example

import com.example.data.database.DatabaseFactory
import com.example.plugins.*
import com.example.routing.configureRouting
import com.example.security.PasswordHasher
import com.example.di.AppContainer
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()
    seedDatabase()
    configureContentNegotiation()
    configureCORS()
    configureCallLogging()
    configureStatusPages()
    configureAuthentication()
    configureRouting()
}

fun seedDatabase() {
    val userRepo = AppContainer.userRepository
    val prizeRepo = AppContainer.prizeRepository

    kotlinx.coroutines.runBlocking {
        // Создаём пользователей если не существуют
        if (userRepo.findByUsername("admin") == null) {
            userRepo.createUser("admin", PasswordHasher.hash("secret123"), "admin")
        }
        if (userRepo.findByUsername("user") == null) {
            userRepo.createUser("user", PasswordHasher.hash("user123"), "user")
        }

        // Заполняем премии если таблица пустая
        if (prizeRepo.getAllPrizes().isEmpty()) {
            seedPrizes()
        }
    }
}

suspend fun seedPrizes() {
    val prizeRepo = AppContainer.prizeRepository as com.example.data.repository.PrizeRepositoryImpl
    prizeRepo.seedData()
}
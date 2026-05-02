package com.example.kotlin_6_module.task3.domain

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): String {
        val token = repository.login(username, password)
        repository.saveToken(token)
        return token
    }
}
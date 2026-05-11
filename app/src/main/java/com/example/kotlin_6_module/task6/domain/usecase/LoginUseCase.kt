package com.example.kotlin_6_module.task6.domain.usecase

import com.example.kotlin_6_module.task6.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): Result<String> {
        return repository.login(username, password)
    }
}
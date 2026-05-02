package com.example.kotlin_6_module.task3.domain

class GetUserDetailUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(id: Int, token: String): User =
        repository.getUserById(id, token)
}
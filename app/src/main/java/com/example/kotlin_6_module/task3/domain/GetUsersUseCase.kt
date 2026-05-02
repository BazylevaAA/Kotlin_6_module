package com.example.kotlin_6_module.task3.domain

class GetUsersUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(token: String): List<User> =
        repository.getUsers(token)
}
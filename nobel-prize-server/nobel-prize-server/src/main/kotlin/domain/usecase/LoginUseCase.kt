package com.example.domain.usecase

import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import com.example.security.PasswordHasher

class LoginUseCase(private val userRepository: UserRepository) {
    suspend fun execute(username: String, password: String): User? {
        val user = userRepository.findByUsername(username) ?: return null
        return if (PasswordHasher.verify(password, user.passwordHash)) user else null
    }
}
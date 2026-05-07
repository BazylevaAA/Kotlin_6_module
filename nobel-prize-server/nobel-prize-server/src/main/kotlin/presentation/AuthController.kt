package com.example.presentation

import com.example.domain.model.LoginRequest
import com.example.domain.usecase.LoginUseCase
import com.example.security.JwtConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class AuthController(private val loginUseCase: LoginUseCase) {
    fun configure(routing: Routing) {
        routing.post("/auth/login") {
            val request = call.receive<LoginRequest>()
            val user = loginUseCase.execute(request.username, request.password)
            if (user == null) {
                call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Invalid credentials"))
                return@post
            }
            val token = JwtConfig.generateToken(user.username, user.role)
            call.respond(mapOf("token" to token))
        }
    }
}
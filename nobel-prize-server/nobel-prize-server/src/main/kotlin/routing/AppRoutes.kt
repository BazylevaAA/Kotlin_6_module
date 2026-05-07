package com.example.routing

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.di.AppModule
import com.example.plugins.JWT_AUDIENCE
import com.example.plugins.JWT_ISSUER
import com.example.plugins.JWT_SECRET
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val token: String
)

private val users = mapOf(
    "admin" to "password123",
    "user" to "user123"
)

fun Application.configureRouting() {
    routing {
        // POST /auth/login — открытый эндпоинт
        post("/auth/login") {
            val request = call.receive<LoginRequest>()
            val expectedPassword = users[request.username]

            if (expectedPassword == null || expectedPassword != request.password) {
                call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
                return@post
            }

            val token = JWT.create()
                .withIssuer(JWT_ISSUER)
                .withAudience(JWT_AUDIENCE)
                .withClaim("username", request.username)
                .withExpiresAt(Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .sign(Algorithm.HMAC256(JWT_SECRET))

            call.respond(LoginResponse(token = token))
        }

        // Защищённые маршруты
        authenticate("auth-jwt") {
            AppModule.nobelPrizeController.configure(this)
        }
    }
}
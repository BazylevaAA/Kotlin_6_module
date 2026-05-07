package com.example.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.JWTVerifier
import java.util.Date

object JwtConfig {
    private const val SECRET = "nobel-prize-secret-key-32-chars!!"
    private const val ISSUER = "ktor-app"
    private const val AUDIENCE = "mobile-app"
    private const val VALIDITY_MS = 30 * 60 * 1000L // 30 минут

    val verifier: JWTVerifier = JWT
        .require(Algorithm.HMAC256(SECRET))
        .withIssuer(ISSUER)
        .withAudience(AUDIENCE)
        .build()

    fun generateToken(username: String, role: String): String = JWT.create()
        .withIssuer(ISSUER)
        .withAudience(AUDIENCE)
        .withClaim("username", username)
        .withClaim("role", role)
        .withExpiresAt(Date(System.currentTimeMillis() + VALIDITY_MS))
        .sign(Algorithm.HMAC256(SECRET))
}
package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

const val JWT_SECRET = "nobel-prize-secret-key-32-chars!!"
const val JWT_ISSUER = "nobel-prize-server"
const val JWT_AUDIENCE = "nobel-prize-client"
const val JWT_REALM = "Nobel Prize API"

fun Application.configureAuthentication() {
    install(Authentication) {
        jwt("auth-jwt") {
            realm = JWT_REALM
            verifier(
                JWT.require(Algorithm.HMAC256(JWT_SECRET))
                    .withIssuer(JWT_ISSUER)
                    .withAudience(JWT_AUDIENCE)
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("username").asString() != null) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }
}
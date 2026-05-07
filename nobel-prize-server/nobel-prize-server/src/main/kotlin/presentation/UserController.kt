package com.example.presentation

import com.example.domain.repository.UserRepository
import com.example.domain.usecase.AddFavoritePrizeUseCase
import com.example.domain.usecase.GetFavoritePrizesUseCase
import com.example.domain.usecase.RemoveFavoritePrizeUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class UserController(
    private val userRepository: UserRepository,
    private val getFavoritePrizesUseCase: GetFavoritePrizesUseCase,
    private val addFavoritePrizeUseCase: AddFavoritePrizeUseCase,
    private val removeFavoritePrizeUseCase: RemoveFavoritePrizeUseCase
) {
    fun configure(routing: Routing) {
        routing.authenticate("auth-jwt") {
            get("/users/me") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal?.payload?.getClaim("username")?.asString()
                    ?: return@get call.respond(HttpStatusCode.Unauthorized)
                val user = userRepository.findByUsername(username)
                    ?: return@get call.respond(HttpStatusCode.NotFound, mapOf("error" to "User not found"))
                call.respond(mapOf("id" to user.id.toString(), "username" to user.username, "role" to user.role))
            }

            get("/users/me/prizes") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal?.payload?.getClaim("username")?.asString()
                    ?: return@get call.respond(HttpStatusCode.Unauthorized)
                val user = userRepository.findByUsername(username)
                    ?: return@get call.respond(HttpStatusCode.NotFound, mapOf("error" to "User not found"))
                val prizes = getFavoritePrizesUseCase.execute(user.id)
                call.respond(prizes)
            }

            post("/users/me/prizes/{prizeId}") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal?.payload?.getClaim("username")?.asString()
                    ?: return@post call.respond(HttpStatusCode.Unauthorized)
                val user = userRepository.findByUsername(username)
                    ?: return@post call.respond(HttpStatusCode.NotFound, mapOf("error" to "User not found"))
                val prizeId = call.parameters["prizeId"]?.toIntOrNull()
                    ?: return@post call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid prizeId"))
                val result = addFavoritePrizeUseCase.execute(user.id, prizeId)
                if (result) call.respond(mapOf("message" to "Prize added to favorites"))
                else call.respond(HttpStatusCode.Conflict, mapOf("error" to "Prize already in favorites"))
            }

            delete("/users/me/prizes/{prizeId}") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal?.payload?.getClaim("username")?.asString()
                    ?: return@delete call.respond(HttpStatusCode.Unauthorized)
                val user = userRepository.findByUsername(username)
                    ?: return@delete call.respond(HttpStatusCode.NotFound, mapOf("error" to "User not found"))
                val prizeId = call.parameters["prizeId"]?.toIntOrNull()
                    ?: return@delete call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid prizeId"))
                val result = removeFavoritePrizeUseCase.execute(user.id, prizeId)
                if (result) call.respond(mapOf("message" to "Prize removed from favorites"))
                else call.respond(HttpStatusCode.NotFound, mapOf("error" to "Prize not in favorites"))
            }
        }
    }
}
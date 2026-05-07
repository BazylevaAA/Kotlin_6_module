package com.example.presentation

import com.example.domain.usecase.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class PrizeController(
    private val getPrizesUseCase: GetPrizesUseCase,
    private val getPrizeUseCase: GetPrizeUseCase,
    private val getLaureatesUseCase: GetLaureatesUseCase,
    private val addFavoritePrizeUseCase: AddFavoritePrizeUseCase,
    private val removeFavoritePrizeUseCase: RemoveFavoritePrizeUseCase,
    private val getFavoritePrizesUseCase: GetFavoritePrizesUseCase
) {
    fun configure(routing: Routing) {
        routing.authenticate("auth-jwt") {
            get("/prizes") {
                val prizes = getPrizesUseCase.execute()
                call.respond(prizes)
            }

            get("/prizes/{year}/{category}") {
                val year = call.parameters["year"]?.toIntOrNull()
                    ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid year"))
                val category = call.parameters["category"]
                    ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid category"))

                val prize = getPrizeUseCase.execute(year, category)
                    ?: return@get call.respond(HttpStatusCode.NotFound, mapOf("error" to "Prize not found"))
                call.respond(prize)
            }

            get("/prizes/{year}/{category}/laureates") {
                val year = call.parameters["year"]?.toIntOrNull()
                    ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid year"))
                val category = call.parameters["category"]
                    ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid category"))

                val laureates = getLaureatesUseCase.execute(year, category)
                call.respond(laureates)
            }
        }
    }
}
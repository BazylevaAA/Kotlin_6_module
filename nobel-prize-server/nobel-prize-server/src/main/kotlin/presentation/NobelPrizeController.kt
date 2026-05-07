package com.example.presentation

import com.example.domain.usecase.GetAllPrizesUseCase
import com.example.domain.usecase.GetPrizeDetailUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class LaureateResponse(
    val id: String,
    val fullName: String,
    val portion: String,
    val motivation: String,
    val portraitUrl: String = ""
)

@Serializable
data class NobelPrizeResponse(
    val year: String,
    val category: String,
    val laureates: List<LaureateResponse> = emptyList()
)

class NobelPrizeController(
    private val getAllPrizesUseCase: GetAllPrizesUseCase,
    private val getPrizeDetailUseCase: GetPrizeDetailUseCase
) {
    fun configure(routing: Route) {
        routing.route("/prizes") {
            get {
                val prizes = getAllPrizesUseCase()
                val response = prizes.map { prize ->
                    NobelPrizeResponse(
                        year = prize.year,
                        category = prize.category,
                        laureates = prize.laureates.map { laureate ->
                            LaureateResponse(
                                id = laureate.id,
                                fullName = laureate.fullName,
                                portion = laureate.portion,
                                motivation = laureate.motivation,
                                portraitUrl = laureate.portraitUrl
                            )
                        }
                    )
                }
                call.respond(response)
            }

            get("/{year}/{category}") {
                val year = call.parameters["year"] ?: return@get call.respond(
                    HttpStatusCode.BadRequest, "Missing year"
                )
                val category = call.parameters["category"] ?: return@get call.respond(
                    HttpStatusCode.BadRequest, "Missing category"
                )
                val prize = getPrizeDetailUseCase(year, category)
                if (prize == null) {
                    call.respond(HttpStatusCode.NotFound, "Prize not found")
                } else {
                    call.respond(
                        NobelPrizeResponse(
                            year = prize.year,
                            category = prize.category,
                            laureates = prize.laureates.map { laureate ->
                                LaureateResponse(
                                    id = laureate.id,
                                    fullName = laureate.fullName,
                                    portion = laureate.portion,
                                    motivation = laureate.motivation,
                                    portraitUrl = laureate.portraitUrl
                                )
                            }
                        )
                    )
                }
            }

            get("/{year}/{category}/laureates") {
                val year = call.parameters["year"] ?: return@get call.respond(
                    HttpStatusCode.BadRequest, "Missing year"
                )
                val category = call.parameters["category"] ?: return@get call.respond(
                    HttpStatusCode.BadRequest, "Missing category"
                )
                val prize = getPrizeDetailUseCase(year, category)
                if (prize == null) {
                    call.respond(HttpStatusCode.NotFound, "Prize not found")
                } else {
                    val laureates = prize.laureates.map { laureate ->
                        LaureateResponse(
                            id = laureate.id,
                            fullName = laureate.fullName,
                            portion = laureate.portion,
                            motivation = laureate.motivation,
                            portraitUrl = laureate.portraitUrl
                        )
                    }
                    call.respond(laureates)
                }
            }
        }
    }
}
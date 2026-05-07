package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(
                text = "500: ${cause.message}",
                status = HttpStatusCode.InternalServerError
            )
        }
        status(HttpStatusCode.NotFound) { call, status ->
            call.respondText(
                text = "404: Not Found",
                status = status
            )
        }
        status(HttpStatusCode.Unauthorized) { call, status ->
            call.respondText(
                text = "401: Unauthorized",
                status = status
            )
        }
    }
}
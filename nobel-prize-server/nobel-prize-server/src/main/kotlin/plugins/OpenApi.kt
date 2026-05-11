package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureOpenApi() {
    routing {
        get("/openapi.json") {
            call.respondText(openApiJson(), ContentType.Application.Json)
        }
        get("/redoc") {
            call.respondText(redocHtml(), ContentType.Text.Html)
        }
    }
}

private fun openApiJson() = """
{
  "openapi": "3.0.0",
  "info": {
    "title": "Nobel Prize API",
    "version": "1.0.0",
    "description": "API для работы с Нобелевскими премиями"
  },
  "components": {
    "securitySchemes": {
      "BearerAuth": { "type": "http", "scheme": "bearer", "bearerFormat": "JWT" }
    }
  },
  "paths": {
    "/auth/login": {
      "post": { "summary": "Авторизация", "responses": { "200": { "description": "JWT токен" } } }
    },
    "/prizes": {
      "get": { "summary": "Список премий", "security": [{ "BearerAuth": [] }], "responses": { "200": { "description": "OK" } } }
    },
    "/prizes/{year}/{category}": {
      "get": { "summary": "Детальная премия", "security": [{ "BearerAuth": [] }], "responses": { "200": { "description": "OK" } } }
    },
    "/prizes/{year}/{category}/laureates": {
      "get": { "summary": "Лауреаты", "security": [{ "BearerAuth": [] }], "responses": { "200": { "description": "OK" } } }
    },
    "/users/me": {
      "get": { "summary": "Профиль", "security": [{ "BearerAuth": [] }], "responses": { "200": { "description": "OK" } } }
    },
    "/users/me/prizes": {
      "get": { "summary": "Избранные", "security": [{ "BearerAuth": [] }], "responses": { "200": { "description": "OK" } } }
    },
    "/users/me/prizes/{prizeId}": {
      "post": { "summary": "Добавить в избранное", "security": [{ "BearerAuth": [] }], "responses": { "200": { "description": "OK" } } },
      "delete": { "summary": "Удалить из избранного", "security": [{ "BearerAuth": [] }], "responses": { "200": { "description": "OK" } } }
    }
  }
}
""".trimIndent()

private fun redocHtml() = """
<!DOCTYPE html>
<html>
<head>
    <title>Nobel Prize API</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>body { margin: 0; padding: 0; }</style>
</head>
<body>
    <redoc spec-url='http://localhost:8080/openapi.json'></redoc>
    <script src="https://cdn.jsdelivr.net/npm/redoc/bundles/redoc.standalone.js"></script>
</body>
</html>
""".trimIndent()
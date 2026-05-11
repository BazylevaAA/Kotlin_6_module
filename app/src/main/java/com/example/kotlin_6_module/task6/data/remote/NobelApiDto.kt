package com.example.kotlin_6_module.task6.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val username: String,
    val password: String
)

@Serializable
data class LoginResponseDto(
    val token: String
)

@Serializable
data class NobelPrizeDto(
    val id: Int,
    val awardYear: String,
    val category: String,
    val fullName: String,
    val motivation: String,
    val detailLink: String
)

@Serializable
data class LaureateDtoItem(
    val id: Int,
    val prizeId: Int,
    val fullName: String,
    val portion: String,
    val motivation: String,
    val portraitUrl: String
)

@Serializable
data class UserDto(
    val id: Int,
    val username: String,
    val role: String
)
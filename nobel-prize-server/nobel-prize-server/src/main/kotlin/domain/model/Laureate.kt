package com.example.domain.model

data class Laureate(
    val id: String,
    val fullName: String,
    val portion: String,
    val motivation: String,
    val portraitUrl: String = ""
)
package com.example.kotlin_6_module.task2.domain

data class Laureate(
    val fullName: String,
    val motivation: String,
    val portraitUrl: String?,
    val birthCountry: String?
)

data class NobelPrize(
    val year: String,
    val category: String,
    val laureates: List<Laureate>
)
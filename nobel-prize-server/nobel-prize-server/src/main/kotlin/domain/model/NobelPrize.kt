package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NobelPrize(
    val id: Int,
    val awardYear: Int,
    val category: String,
    val fullName: String,
    val motivation: String,
    val detailLink: String? = null,
    val laureates: List<Laureate> = emptyList()
)
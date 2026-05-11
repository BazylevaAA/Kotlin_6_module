package com.example.kotlin_6_module.task6.domain.model

data class NobelPrize(
    val id: Int,
    val awardYear: String,
    val category: String,
    val fullName: String,
    val motivation: String,
    val detailLink: String
)
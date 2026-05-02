package com.example.kotlin_6_module.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LaureateDto(
    @SerialName("fullName") val fullName: Map<String, String>? = null,
    @SerialName("motivation") val motivation: Map<String, String>? = null,
    @SerialName("birth") val birth: BirthDto? = null,
    @SerialName("links") val links: List<LinkDto>? = null
)

@Serializable
data class BirthDto(
    @SerialName("place") val place: PlaceDto? = null
)

@Serializable
data class PlaceDto(
    @SerialName("country") val country: Map<String, String>? = null
)

@Serializable
data class LinkDto(
    @SerialName("rel") val rel: String? = null,
    @SerialName("href") val href: String? = null
)

@Serializable
data class NobelPrizeDto(
    @SerialName("awardYear") val awardYear: String = "",
    @SerialName("category") val category: Map<String, String>? = null,
    @SerialName("laureates") val laureates: List<LaureateDto>? = null
)

@Serializable
data class NobelPrizesResponse(
    @SerialName("nobelPrizes") val nobelPrizes: List<NobelPrizeDto> = emptyList()
)
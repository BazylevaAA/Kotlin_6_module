package com.example.kotlin_6_module.task2.data

import com.example.kotlin_6_module.task2.domain.Laureate
import com.example.kotlin_6_module.task2.domain.NobelPrize
import com.example.kotlin_6_module.task2.domain.NobelPrizeRepository

class NobelPrizeRepositoryImpl(private val api: NobelPrizeApi) : NobelPrizeRepository {

    override suspend fun getPrizes(year: String?, category: String?): List<NobelPrize> {
        return api.getPrizes(year, category).nobelPrizes.map { dto ->
            NobelPrize(
                year = dto.awardYear,
                category = dto.category?.get("en") ?: "",
                laureates = dto.laureates?.map { l ->
                    Laureate(
                        fullName = l.fullName?.get("en") ?: "Organization",
                        motivation = l.motivation?.get("en") ?: "",
                        portraitUrl = l.links?.firstOrNull { it.rel == "portrait" }?.href,
                        birthCountry = l.birth?.place?.country?.get("en")
                    )
                } ?: emptyList()
            )
        }
    }
}
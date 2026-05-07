package com.example.data.repository

import com.example.domain.model.Laureate
import com.example.domain.model.NobelPrize
import com.example.domain.repository.NobelPrizeRepository

class NobelPrizeRepositoryImpl : NobelPrizeRepository {

    private val prizes: List<NobelPrize> = listOf(
        NobelPrize(
            year = "2023",
            category = "physics",
            laureates = listOf(
                Laureate(
                    id = "1",
                    fullName = "Pierre Agostini",
                    portion = "1/3",
                    motivation = "for experimental methods that generate attosecond pulses of light for the study of electron dynamics in matter"
                ),
                Laureate(
                    id = "2",
                    fullName = "Ferenc Krausz",
                    portion = "1/3",
                    motivation = "for experimental methods that generate attosecond pulses of light for the study of electron dynamics in matter"
                ),
                Laureate(
                    id = "3",
                    fullName = "Anne L'Huillier",
                    portion = "1/3",
                    motivation = "for experimental methods that generate attosecond pulses of light for the study of electron dynamics in matter"
                )
            )
        ),
        NobelPrize(
            year = "2023",
            category = "chemistry",
            laureates = listOf(
                Laureate(
                    id = "4",
                    fullName = "Moungi G. Bawendi",
                    portion = "1/3",
                    motivation = "for the discovery and synthesis of quantum dots"
                ),
                Laureate(
                    id = "5",
                    fullName = "Louis E. Brus",
                    portion = "1/3",
                    motivation = "for the discovery and synthesis of quantum dots"
                ),
                Laureate(
                    id = "6",
                    fullName = "Alexei I. Ekimov",
                    portion = "1/3",
                    motivation = "for the discovery and synthesis of quantum dots"
                )
            )
        ),
        NobelPrize(
            year = "2023",
            category = "medicine",
            laureates = listOf(
                Laureate(
                    id = "7",
                    fullName = "Katalin Karikó",
                    portion = "1/2",
                    motivation = "for discoveries concerning nucleoside base modifications that enabled the development of effective mRNA vaccines against COVID-19"
                ),
                Laureate(
                    id = "8",
                    fullName = "Drew Weissman",
                    portion = "1/2",
                    motivation = "for discoveries concerning nucleoside base modifications that enabled the development of effective mRNA vaccines against COVID-19"
                )
            )
        ),
        NobelPrize(
            year = "2023",
            category = "peace",
            laureates = listOf(
                Laureate(
                    id = "9",
                    fullName = "Narges Mohammadi",
                    portion = "1/1",
                    motivation = "for her fight against the oppression of women in Iran and her efforts to promote human rights and freedom for all"
                )
            )
        ),
        NobelPrize(
            year = "2022",
            category = "physics",
            laureates = listOf(
                Laureate(
                    id = "10",
                    fullName = "Alain Aspect",
                    portion = "1/3",
                    motivation = "for experiments with entangled photons, establishing the violation of Bell inequalities and pioneering quantum information science"
                ),
                Laureate(
                    id = "11",
                    fullName = "John F. Clauser",
                    portion = "1/3",
                    motivation = "for experiments with entangled photons, establishing the violation of Bell inequalities and pioneering quantum information science"
                ),
                Laureate(
                    id = "12",
                    fullName = "Anton Zeilinger",
                    portion = "1/3",
                    motivation = "for experiments with entangled photons, establishing the violation of Bell inequalities and pioneering quantum information science"
                )
            )
        ),
        NobelPrize(
            year = "2022",
            category = "chemistry",
            laureates = listOf(
                Laureate(
                    id = "13",
                    fullName = "Carolyn R. Bertozzi",
                    portion = "1/3",
                    motivation = "for the development of click chemistry and bioorthogonal chemistry"
                ),
                Laureate(
                    id = "14",
                    fullName = "Morten Meldal",
                    portion = "1/3",
                    motivation = "for the development of click chemistry and bioorthogonal chemistry"
                ),
                Laureate(
                    id = "15",
                    fullName = "K. Barry Sharpless",
                    portion = "1/3",
                    motivation = "for the development of click chemistry and bioorthogonal chemistry"
                )
            )
        )
    )

    override fun getAll(): List<NobelPrize> = prizes

    override fun getByYearAndCategory(year: String, category: String): NobelPrize? =
        prizes.find { it.year == year && it.category == category }
}
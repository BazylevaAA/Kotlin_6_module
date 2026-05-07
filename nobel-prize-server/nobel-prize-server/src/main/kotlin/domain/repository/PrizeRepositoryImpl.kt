package com.example.data.repository

import com.example.data.database.LaureateTable
import com.example.data.database.PrizeTable
import com.example.data.database.UserPrizeTable
import com.example.domain.model.Laureate
import com.example.domain.model.NobelPrize
import com.example.domain.repository.PrizeRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.LocalDateTime

class PrizeRepositoryImpl : PrizeRepository {

    private fun ResultRow.toPrize() = NobelPrize(
        id = this[PrizeTable.id].value,
        awardYear = this[PrizeTable.awardYear],
        category = this[PrizeTable.category],
        fullName = this[PrizeTable.fullName],
        motivation = this[PrizeTable.motivation],
        detailLink = this[PrizeTable.detailLink]
    )

    private fun ResultRow.toLaureate() = Laureate(
        id = this[LaureateTable.id].value,
        prizeId = this[LaureateTable.prizeId].value,
        fullName = this[LaureateTable.fullName],
        portion = this[LaureateTable.portion],
        motivation = this[LaureateTable.motivation],
        portraitUrl = this[LaureateTable.portraitUrl]
    )

    override suspend fun getAllPrizes(): List<NobelPrize> =
        newSuspendedTransaction {
            PrizeTable.selectAll().map { it.toPrize() }
        }

    override suspend fun getPrize(year: Int, category: String): NobelPrize? =
        newSuspendedTransaction {
            PrizeTable.selectAll()
                .where { (PrizeTable.awardYear eq year) and (PrizeTable.category eq category) }
                .map { it.toPrize() }
                .firstOrNull()
        }

    override suspend fun getLaureates(year: Int, category: String): List<Laureate> =
        newSuspendedTransaction {
            val prize = PrizeTable.selectAll()
                .where { (PrizeTable.awardYear eq year) and (PrizeTable.category eq category) }
                .firstOrNull() ?: return@newSuspendedTransaction emptyList()

            val prizeId = prize[PrizeTable.id].value
            LaureateTable.selectAll()
                .where { LaureateTable.prizeId eq prizeId }
                .map { it.toLaureate() }
        }

    override suspend fun getFavoritePrizes(userId: Int): List<NobelPrize> =
        newSuspendedTransaction {
            (UserPrizeTable innerJoin PrizeTable)
                .selectAll()
                .where { UserPrizeTable.userId eq userId }
                .map {
                    NobelPrize(
                        id = it[PrizeTable.id].value,
                        awardYear = it[PrizeTable.awardYear],
                        category = it[PrizeTable.category],
                        fullName = it[PrizeTable.fullName],
                        motivation = it[PrizeTable.motivation],
                        detailLink = it[PrizeTable.detailLink]
                    )
                }
        }

    override suspend fun addFavoritePrize(userId: Int, prizeId: Int): Boolean =
        newSuspendedTransaction {
            val exists = UserPrizeTable.selectAll()
                .where { (UserPrizeTable.userId eq userId) and (UserPrizeTable.prizeId eq prizeId) }
                .firstOrNull()
            if (exists != null) return@newSuspendedTransaction false
            UserPrizeTable.insert {
                it[UserPrizeTable.userId] = userId
                it[UserPrizeTable.prizeId] = prizeId
                it[addedAt] = LocalDateTime.now()
            }
            true
        }

    override suspend fun removeFavoritePrize(userId: Int, prizeId: Int): Boolean =
        newSuspendedTransaction {
            val deleted = UserPrizeTable.deleteWhere {
                (UserPrizeTable.userId eq userId) and (UserPrizeTable.prizeId eq prizeId)
            }
            deleted > 0
        }

    suspend fun seedData() {
        newSuspendedTransaction {
            val physics2023 = PrizeTable.insert {
                it[awardYear] = 2023
                it[category] = "physics"
                it[fullName] = "Nobel Prize in Physics 2023"
                it[motivation] = "for experimental methods that generate attosecond pulses of light for the study of electron dynamics in matter"
                it[detailLink] = "https://www.nobelprize.org/prizes/physics/2023/summary/"
            }[PrizeTable.id].value

            LaureateTable.insert {
                it[prizeId] = physics2023
                it[fullName] = "Pierre Agostini"
                it[portion] = "1/3"
                it[motivation] = "for experimental methods that generate attosecond pulses of light for the study of electron dynamics in matter"
                it[portraitUrl] = "https://www.nobelprize.org/images/agostini-185343-portrait-mini-2x.jpg"
            }
            LaureateTable.insert {
                it[prizeId] = physics2023
                it[fullName] = "Ferenc Krausz"
                it[portion] = "1/3"
                it[motivation] = "for experimental methods that generate attosecond pulses of light for the study of electron dynamics in matter"
                it[portraitUrl] = "https://www.nobelprize.org/images/krausz-185345-portrait-mini-2x.jpg"
            }
            LaureateTable.insert {
                it[prizeId] = physics2023
                it[fullName] = "Anne L'Huillier"
                it[portion] = "1/3"
                it[motivation] = "for experimental methods that generate attosecond pulses of light for the study of electron dynamics in matter"
                it[portraitUrl] = "https://www.nobelprize.org/images/lhuillier-185347-portrait-mini-2x.jpg"
            }

            val chemistry2023 = PrizeTable.insert {
                it[awardYear] = 2023
                it[category] = "chemistry"
                it[fullName] = "Nobel Prize in Chemistry 2023"
                it[motivation] = "for the discovery and synthesis of quantum dots"
                it[detailLink] = "https://www.nobelprize.org/prizes/chemistry/2023/summary/"
            }[PrizeTable.id].value

            LaureateTable.insert {
                it[prizeId] = chemistry2023
                it[fullName] = "Moungi G. Bawendi"
                it[portion] = "1/3"
                it[motivation] = "for the discovery and synthesis of quantum dots"
                it[portraitUrl] = "https://www.nobelprize.org/images/bawendi-185364-portrait-mini-2x.jpg"
            }
            LaureateTable.insert {
                it[prizeId] = chemistry2023
                it[fullName] = "Louis E. Brus"
                it[portion] = "1/3"
                it[motivation] = "for the discovery and synthesis of quantum dots"
                it[portraitUrl] = "https://www.nobelprize.org/images/brus-185366-portrait-mini-2x.jpg"
            }
            LaureateTable.insert {
                it[prizeId] = chemistry2023
                it[fullName] = "Alexei I. Ekimov"
                it[portion] = "1/3"
                it[motivation] = "for the discovery and synthesis of quantum dots"
                it[portraitUrl] = "https://www.nobelprize.org/images/ekimov-185368-portrait-mini-2x.jpg"
            }

            val medicine2023 = PrizeTable.insert {
                it[awardYear] = 2023
                it[category] = "medicine"
                it[fullName] = "Nobel Prize in Physiology or Medicine 2023"
                it[motivation] = "for their discoveries concerning nucleoside base modifications that enabled the development of effective mRNA vaccines against COVID-19"
                it[detailLink] = "https://www.nobelprize.org/prizes/medicine/2023/summary/"
            }[PrizeTable.id].value

            LaureateTable.insert {
                it[prizeId] = medicine2023
                it[fullName] = "Katalin Karikó"
                it[portion] = "1/2"
                it[motivation] = "for their discoveries concerning nucleoside base modifications that enabled the development of effective mRNA vaccines against COVID-19"
                it[portraitUrl] = "https://www.nobelprize.org/images/kariko-185376-portrait-mini-2x.jpg"
            }
            LaureateTable.insert {
                it[prizeId] = medicine2023
                it[fullName] = "Drew Weissman"
                it[portion] = "1/2"
                it[motivation] = "for their discoveries concerning nucleoside base modifications that enabled the development of effective mRNA vaccines against COVID-19"
                it[portraitUrl] = "https://www.nobelprize.org/images/weissman-185378-portrait-mini-2x.jpg"
            }

            val physics2022 = PrizeTable.insert {
                it[awardYear] = 2022
                it[category] = "physics"
                it[fullName] = "Nobel Prize in Physics 2022"
                it[motivation] = "for experiments with entangled photons, establishing the violation of Bell inequalities and pioneering quantum information science"
                it[detailLink] = "https://www.nobelprize.org/prizes/physics/2022/summary/"
            }[PrizeTable.id].value

            LaureateTable.insert {
                it[prizeId] = physics2022
                it[fullName] = "Alain Aspect"
                it[portion] = "1/3"
                it[motivation] = "for experiments with entangled photons, establishing the violation of Bell inequalities and pioneering quantum information science"
                it[portraitUrl] = "https://www.nobelprize.org/images/aspect-185163-portrait-mini-2x.jpg"
            }
            LaureateTable.insert {
                it[prizeId] = physics2022
                it[fullName] = "John F. Clauser"
                it[portion] = "1/3"
                it[motivation] = "for experiments with entangled photons, establishing the violation of Bell inequalities and pioneering quantum information science"
                it[portraitUrl] = "https://www.nobelprize.org/images/clauser-185165-portrait-mini-2x.jpg"
            }
            LaureateTable.insert {
                it[prizeId] = physics2022
                it[fullName] = "Anton Zeilinger"
                it[portion] = "1/3"
                it[motivation] = "for experiments with entangled photons, establishing the violation of Bell inequalities and pioneering quantum information science"
                it[portraitUrl] = "https://www.nobelprize.org/images/zeilinger-185167-portrait-mini-2x.jpg"
            }

            val peace2022 = PrizeTable.insert {
                it[awardYear] = 2022
                it[category] = "peace"
                it[fullName] = "Nobel Peace Prize 2022"
                it[motivation] = "for their efforts to document war crimes, human right abuses and the abuse of power"
                it[detailLink] = "https://www.nobelprize.org/prizes/peace/2022/summary/"
            }[PrizeTable.id].value

            LaureateTable.insert {
                it[prizeId] = peace2022
                it[fullName] = "Ales Bialiatski"
                it[portion] = "1/3"
                it[motivation] = "for their efforts to document war crimes, human right abuses and the abuse of power"
                it[portraitUrl] = "https://www.nobelprize.org/images/bialiatski-185200-portrait-mini-2x.jpg"
            }
            LaureateTable.insert {
                it[prizeId] = peace2022
                it[fullName] = "Memorial (organization)"
                it[portion] = "1/3"
                it[motivation] = "for their efforts to document war crimes, human right abuses and the abuse of power"
                it[portraitUrl] = null
            }
            LaureateTable.insert {
                it[prizeId] = peace2022
                it[fullName] = "Center for Civil Liberties (organization)"
                it[portion] = "1/3"
                it[motivation] = "for their efforts to document war crimes, human right abuses and the abuse of power"
                it[portraitUrl] = null
            }
        }
    }
}
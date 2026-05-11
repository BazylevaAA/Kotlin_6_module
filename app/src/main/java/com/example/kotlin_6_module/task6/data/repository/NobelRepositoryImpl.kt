package com.example.kotlin_6_module.task6.data.repository

import com.example.kotlin_6_module.task6.data.remote.NobelApiClient
import com.example.kotlin_6_module.task6.domain.model.Laureate
import com.example.kotlin_6_module.task6.domain.model.NobelPrize
import com.example.kotlin_6_module.task6.domain.model.User
import com.example.kotlin_6_module.task6.domain.repository.NobelRepository

class NobelRepositoryImpl(
    private val apiClient: NobelApiClient
) : NobelRepository {

    override suspend fun getPrizes(token: String, year: String?, category: String?): Result<List<NobelPrize>> {
        return try {
            val dtos = apiClient.getPrizes(token, year, category)
            Result.success(dtos.map {
                NobelPrize(
                    id = it.id,
                    awardYear = it.awardYear,
                    category = it.category,
                    fullName = it.fullName,
                    motivation = it.motivation,
                    detailLink = it.detailLink
                )
            })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getLaureates(token: String, year: String, category: String): Result<List<Laureate>> {
        return try {
            val dtos = apiClient.getLaureates(token, year, category)
            Result.success(dtos.map {
                Laureate(
                    id = it.id,
                    prizeId = it.prizeId,
                    fullName = it.fullName,
                    portion = it.portion,
                    motivation = it.motivation,
                    portraitUrl = it.portraitUrl
                )
            })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMe(token: String): Result<User> {
        return try {
            val dto = apiClient.getMe(token)
            Result.success(User(dto.id, dto.username, dto.role))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFavoritePrizes(token: String): Result<List<NobelPrize>> {
        return try {
            val dtos = apiClient.getFavoritePrizes(token)
            Result.success(dtos.map {
                NobelPrize(
                    id = it.id,
                    awardYear = it.awardYear,
                    category = it.category,
                    fullName = it.fullName,
                    motivation = it.motivation,
                    detailLink = it.detailLink
                )
            })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addFavorite(token: String, prizeId: Int): Result<Unit> {
        return try {
            apiClient.addFavorite(token, prizeId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeFavorite(token: String, prizeId: Int): Result<Unit> {
        return try {
            apiClient.removeFavorite(token, prizeId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
package com.example.kotlin_6_module.domain

interface PhotoRepository {
    suspend fun getPhotos(page: Int, limit: Int): List<Photo>
}
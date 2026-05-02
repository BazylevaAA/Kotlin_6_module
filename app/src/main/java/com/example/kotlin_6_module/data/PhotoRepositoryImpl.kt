package com.example.kotlin_6_module.data

import com.example.kotlin_6_module.domain.Photo
import com.example.kotlin_6_module.domain.PhotoRepository

class PhotoRepositoryImpl(private val api: PicsumApi) : PhotoRepository {
    override suspend fun getPhotos(page: Int, limit: Int): List<Photo> {
        return api.getPhotos(page, limit).map { dto ->
            Photo(
                id = dto.id,
                author = dto.author,
                width = dto.width,
                height = dto.height,
                url = dto.url,
                downloadUrl = dto.downloadUrl
            )
        }
    }
}
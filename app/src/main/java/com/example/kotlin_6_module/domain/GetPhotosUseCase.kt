package com.example.kotlin_6_module.domain

class GetPhotosUseCase(private val repository: PhotoRepository) {
    suspend operator fun invoke(page: Int = 1, limit: Int = 20): List<Photo> {
        return repository.getPhotos(page, limit)
    }
}
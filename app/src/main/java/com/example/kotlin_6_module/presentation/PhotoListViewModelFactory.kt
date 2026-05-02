package com.example.kotlin_6_module.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_6_module.domain.GetPhotosUseCase

class PhotoListViewModelFactory(
    private val getPhotosUseCase: GetPhotosUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return PhotoListViewModel(getPhotosUseCase) as T
    }
}
package com.example.kotlin_6_module.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_6_module.domain.Photo
import com.example.kotlin_6_module.domain.GetPhotosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class PhotoListState {
    object Loading : PhotoListState()
    data class Success(val photos: List<Photo>) : PhotoListState()
    data class Error(val message: String) : PhotoListState()
}

class PhotoListViewModel(private val getPhotosUseCase: GetPhotosUseCase) : ViewModel() {

    private val _state = MutableStateFlow<PhotoListState>(PhotoListState.Loading)
    val state: StateFlow<PhotoListState> = _state

    init {
        loadPhotos()
    }

    fun loadPhotos() {
        viewModelScope.launch {
            _state.value = PhotoListState.Loading
            try {
                val photos = getPhotosUseCase()
                _state.value = PhotoListState.Success(photos)
            } catch (e: Exception) {
                _state.value = PhotoListState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }
}
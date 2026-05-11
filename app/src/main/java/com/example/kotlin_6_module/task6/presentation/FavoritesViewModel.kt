package com.example.kotlin_6_module.task6.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_6_module.task6.domain.model.NobelPrize
import com.example.kotlin_6_module.task6.domain.usecase.GetFavoritesUseCase
import com.example.kotlin_6_module.task6.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class FavoritesState {
    object Loading : FavoritesState()
    data class Success(val prizes: List<NobelPrize>) : FavoritesState()
    data class Error(val message: String) : FavoritesState()
}

class FavoritesViewModel(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val token: String
) : ViewModel() {

    private val _state = MutableStateFlow<FavoritesState>(FavoritesState.Loading)
    val state: StateFlow<FavoritesState> = _state

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch {
            _state.value = FavoritesState.Loading
            getFavoritesUseCase(token).fold(
                onSuccess = { _state.value = FavoritesState.Success(it) },
                onFailure = { _state.value = FavoritesState.Error("Ошибка: ${it.message}") }
            )
        }
    }

    fun removeFavorite(prizeId: Int) {
        viewModelScope.launch {
            toggleFavoriteUseCase.remove(token, prizeId)
            loadFavorites()
        }
    }
}
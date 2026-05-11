package com.example.kotlin_6_module.task6.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_6_module.task6.domain.model.NobelPrize
import com.example.kotlin_6_module.task6.domain.usecase.GetFavoritesUseCase
import com.example.kotlin_6_module.task6.domain.usecase.GetPrizesUseCase
import com.example.kotlin_6_module.task6.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class PrizesState {
    object Loading : PrizesState()
    data class Success(val prizes: List<NobelPrize>, val favorites: Set<Int> = emptySet()) : PrizesState()
    data class Error(val message: String) : PrizesState()
}

class PrizesViewModel(
    private val getPrizesUseCase: GetPrizesUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val token: String
) : ViewModel() {

    private val _state = MutableStateFlow<PrizesState>(PrizesState.Loading)
    val state: StateFlow<PrizesState> = _state

    val categories = listOf("", "physics", "chemistry", "medicine", "peace", "literature", "economics")

    private val _selectedYear = MutableStateFlow<String>("")
    val selectedYear: StateFlow<String> = _selectedYear

    private val _selectedCategory = MutableStateFlow<String>("")
    val selectedCategory: StateFlow<String> = _selectedCategory

    init {
        loadPrizes()
    }

    fun setYear(year: String) {
        _selectedYear.value = year
        loadPrizes()
    }

    fun setCategory(category: String) {
        _selectedCategory.value = category
        loadPrizes()
    }

    fun loadPrizes() {
        viewModelScope.launch {
            _state.value = PrizesState.Loading
            val year = _selectedYear.value.takeIf { it.isNotEmpty() }
            val category = _selectedCategory.value.takeIf { it.isNotEmpty() }
            val prizesResult = getPrizesUseCase(token, year, category)
            val favoritesResult = getFavoritesUseCase(token)
            prizesResult.fold(
                onSuccess = { prizes ->
                    val favoriteIds = favoritesResult.getOrDefault(emptyList()).map { it.id }.toSet()
                    _state.value = PrizesState.Success(prizes, favoriteIds)
                },
                onFailure = {
                    _state.value = PrizesState.Error("Ошибка загрузки: ${it.message}")
                }
            )
        }
    }

    fun toggleFavorite(prizeId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            if (isFavorite) {
                toggleFavoriteUseCase.remove(token, prizeId)
            } else {
                toggleFavoriteUseCase.add(token, prizeId)
            }
            loadPrizes()
        }
    }
}
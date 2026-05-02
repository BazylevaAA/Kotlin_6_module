package com.example.kotlin_6_module.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_6_module.task2.data.nobelPrizeHttpClient
import com.example.kotlin_6_module.task2.data.NobelPrizeApi
import com.example.kotlin_6_module.data.NobelPrizeRepositoryImpl
import com.example.kotlin_6_module.task2.domain.GetPrizesUseCase
import com.example.kotlin_6_module.domain.NobelPrize
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class NobelUiState {
    object Loading : NobelUiState()
    data class Success(val prizes: List<NobelPrize>) : NobelUiState()
    data class Error(val message: String) : NobelUiState()
}

class NobelPrizeViewModel : ViewModel() {

    private val useCase = GetPrizesUseCase(
        NobelPrizeRepositoryImpl(
            NobelPrizeApi(nobelPrizeHttpClient)
        )
    )

    private val _uiState = MutableStateFlow<NobelUiState>(NobelUiState.Loading)
    val uiState: StateFlow<NobelUiState> = _uiState

    var selectedYear = MutableStateFlow("")
    var selectedCategory = MutableStateFlow("")

    init {
        loadPrizes()
    }

    fun loadPrizes() {
        viewModelScope.launch {
            _uiState.value = NobelUiState.Loading
            try {
                val prizes = useCase(
                    selectedYear.value.ifBlank { null },
                    selectedCategory.value.ifBlank { null }
                )
                _uiState.value = NobelUiState.Success(prizes)
            } catch (e: Exception) {
                _uiState.value = NobelUiState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }
}
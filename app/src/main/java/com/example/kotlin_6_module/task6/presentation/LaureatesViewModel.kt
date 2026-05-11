package com.example.kotlin_6_module.task6.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_6_module.task6.domain.model.Laureate
import com.example.kotlin_6_module.task6.domain.usecase.GetLaureatesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class LaureatesState {
    object Loading : LaureatesState()
    data class Success(val laureates: List<Laureate>) : LaureatesState()
    data class Error(val message: String) : LaureatesState()
}

class LaureatesViewModel(
    private val getLaureatesUseCase: GetLaureatesUseCase,
    private val token: String,
    private val year: String,
    private val category: String
) : ViewModel() {

    private val _state = MutableStateFlow<LaureatesState>(LaureatesState.Loading)
    val state: StateFlow<LaureatesState> = _state

    init {
        loadLaureates()
    }

    fun loadLaureates() {
        viewModelScope.launch {
            _state.value = LaureatesState.Loading
            getLaureatesUseCase(token, year, category).fold(
                onSuccess = { _state.value = LaureatesState.Success(it) },
                onFailure = { _state.value = LaureatesState.Error("Ошибка: ${it.message}") }
            )
        }
    }
}
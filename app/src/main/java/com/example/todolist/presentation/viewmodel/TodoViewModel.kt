package com.example.todolist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.usecase.GetTodosUseCase
import com.example.todolist.domain.usecase.ToggleTodoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ViewModel: хранит состояние списка и обрабатывает события
class TodoViewModel(
    private val getTodosUseCase: GetTodosUseCase,
    private val toggleTodoUseCase: ToggleTodoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<TodoUiState>(TodoUiState.Loading)
    val uiState: StateFlow<TodoUiState> = _uiState.asStateFlow()

    init {
        // Загружаем список при создании ViewModel
        loadTodos()
    }

    fun loadTodos() {
        viewModelScope.launch {
            _uiState.value = TodoUiState.Loading
            try {
                val items = getTodosUseCase()
                _uiState.value = TodoUiState.Content(items)
            } catch (e: Exception) {
                _uiState.value = TodoUiState.Error(message = e.message ?: "Unknown error")
            }
        }
    }

    fun toggleTodo(id: Int) {
        viewModelScope.launch {
            try {
                toggleTodoUseCase(id)
                // После toggle перечитываем список (из кеша репозитория)
                val items = getTodosUseCase()
                _uiState.value = TodoUiState.Content(items)
            } catch (e: Exception) {
                _uiState.value = TodoUiState.Error(message = e.message ?: "Unknown error")
            }
        }
    }
}

package com.example.todolist.presentation.viewmodel

import com.example.todolist.domain.model.TodoItem

sealed class TodoUiState {
    data object Loading : TodoUiState()
    data class Content(val items: List<TodoItem>) : TodoUiState()
    data class Error(val message: String) : TodoUiState()
}

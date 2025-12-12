package com.example.todolist.domain.model

// Доменная модель: с ней работает приложение (UI/UseCases)
data class TodoItem(
    val id: Int,
    val title: String,
    val description: String,
    val isCompleted: Boolean
)

package com.example.todolist.data.model

// DTO-модель: как данные лежат в JSON
data class TodoItemDto(
    val id: Int,
    val title: String,
    val description: String,
    val isCompleted: Boolean
)

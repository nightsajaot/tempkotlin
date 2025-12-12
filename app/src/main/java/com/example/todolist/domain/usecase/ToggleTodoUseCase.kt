package com.example.todolist.domain.usecase

import com.example.todolist.domain.repository.TodoRepository

// UseCase: переключить выполнение задачи по id (локально)
class ToggleTodoUseCase(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.toggleTodo(id)
    }
}

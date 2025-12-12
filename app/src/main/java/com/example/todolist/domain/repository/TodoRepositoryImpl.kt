package com.example.todolist.data.repository

import com.example.todolist.data.local.TodoJsonDataSource
import com.example.todolist.data.model.TodoItemDto
import com.example.todolist.domain.model.TodoItem
import com.example.todolist.domain.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Репозиторий-заглушка: грузит из JSON один раз и хранит в памяти
class TodoRepositoryImpl(
    private val dataSource: TodoJsonDataSource
) : TodoRepository {

    // Локальное хранилище в памяти (для toggle без базы)
    private var cached: MutableList<TodoItemDto>? = null

    override suspend fun getTodos(): List<TodoItem> = withContext(Dispatchers.IO) {
        // Если ещё не загружали — читаем JSON и кешируем
        val list = cached ?: dataSource.getTodos().toMutableList().also { cached = it }

        // Маппинг DTO -> Domain
        list.map { it.toDomain() }
    }

    override suspend fun toggleTodo(id: Int) = withContext(Dispatchers.IO) {
        val list = cached ?: dataSource.getTodos().toMutableList().also { cached = it }

        val index = list.indexOfFirst { it.id == id }
        if (index >= 0) {
            val current = list[index]
            list[index] = current.copy(isCompleted = !current.isCompleted)
        }
    }

    // Маппер DTO -> Domain
    private fun TodoItemDto.toDomain(): TodoItem {
        return TodoItem(
            id = id,
            title = title,
            description = description,
            isCompleted = isCompleted
        )
    }
}

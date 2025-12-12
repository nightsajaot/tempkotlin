package com.example.todolist

import com.example.todolist.domain.model.TodoItem
import com.example.todolist.domain.repository.TodoRepository
import com.example.todolist.domain.usecase.GetTodosUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetTodosUseCaseTest {

    private class FakeTodoRepository : TodoRepository {

        private val items = mutableListOf(
            TodoItem(1, "A", "A desc", false),
            TodoItem(2, "B", "B desc", true),
            TodoItem(3, "C", "C desc", false)
        )

        override suspend fun getTodos(): List<TodoItem> =
            items.toList()

        override suspend fun toggleTodo(id: Int) {
            val index = items.indexOfFirst { it.id == id }
            if (index != -1) {
                val current = items[index]
                items[index] = current.copy(isCompleted = !current.isCompleted)
            }
        }
    }

    @Test
    fun `GetTodosUseCase returns 3 todos`() = runTest {
        val repository = FakeTodoRepository()
        val useCase = GetTodosUseCase(repository)

        val result = useCase()

        assertEquals(3, result.size)
    }
}

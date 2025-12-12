package com.example.todolist.data.local

import android.content.Context
import com.example.todolist.data.model.TodoItemDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// DataSource: читает JSON из assets/todos.json
class TodoJsonDataSource(
    private val context: Context
) {
    private val gson = Gson()

    fun getTodos(): List<TodoItemDto> {
        // Читаем файл из assets
        val json = context.assets.open("todos.json").bufferedReader().use { it.readText() }

        // Парсим JSON как List<TodoItemDto>
        val type = object : TypeToken<List<TodoItemDto>>() {}.type
        return gson.fromJson(json, type)
    }
}

package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.todolist.data.local.TodoJsonDataSource
import com.example.todolist.data.repository.TodoRepositoryImpl
import com.example.todolist.domain.usecase.GetTodosUseCase
import com.example.todolist.domain.usecase.ToggleTodoUseCase
import com.example.todolist.navigation.AppNavGraph
import com.example.todolist.presentation.viewmodel.TodoViewModel
import com.example.todolist.presentation.viewmodel.TodoViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataSource = TodoJsonDataSource(applicationContext)
        val repository = TodoRepositoryImpl(dataSource)

        val getTodosUseCase = GetTodosUseCase(repository)
        val toggleTodoUseCase = ToggleTodoUseCase(repository)

        val factory = TodoViewModelFactory(getTodosUseCase, toggleTodoUseCase)
        val viewModel = ViewModelProvider(this, factory)[TodoViewModel::class.java]

        setContent {
            val navController = rememberNavController()

            AppNavGraph(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

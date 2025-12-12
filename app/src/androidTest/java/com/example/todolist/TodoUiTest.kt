package com.example.todolist

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.todolist.data.local.TodoJsonDataSource
import com.example.todolist.data.repository.TodoRepositoryImpl
import com.example.todolist.domain.usecase.GetTodosUseCase
import com.example.todolist.domain.usecase.ToggleTodoUseCase
import com.example.todolist.navigation.AppNavGraph
import com.example.todolist.presentation.viewmodel.TodoViewModel
import com.example.todolist.presentation.viewmodel.TodoViewModelFactory
import org.junit.Rule
import org.junit.Test

class TodoUiTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    private fun buildViewModel(activity: ComponentActivity): TodoViewModel {
        val dataSource = TodoJsonDataSource(activity.applicationContext)
        val repository = TodoRepositoryImpl(dataSource)

        val getTodosUseCase = GetTodosUseCase(repository)
        val toggleTodoUseCase = ToggleTodoUseCase(repository)

        val factory = TodoViewModelFactory(getTodosUseCase, toggleTodoUseCase)
        return ViewModelProvider(activity, factory)[TodoViewModel::class.java]
    }

    @Test
    fun ui_shows_all_3_todos_from_json() {
        rule.setContent {
            val vm = buildViewModel(rule.activity)
            val navController = rememberNavController()
            AppNavGraph(navController = navController, viewModel = vm)
        }

        rule.onNodeWithText("Купить молоко").assertExists()
        rule.onNodeWithText("Позвонить маме").assertExists()
        rule.onNodeWithText("Сделать ДЗ по Android").assertExists()
    }

    @Test
    fun ui_checkbox_toggles_status() {
        rule.setContent {
            val vm = buildViewModel(rule.activity)
            val navController = rememberNavController()
            AppNavGraph(navController = navController, viewModel = vm)
        }

        rule.onNodeWithTag("todo_checkbox_1").assertIsOff()
        rule.onNodeWithTag("todo_checkbox_1").performClick()
        rule.onNodeWithTag("todo_checkbox_1").assertIsOn()
    }

    @Test
    fun ui_navigation_list_to_detail_to_list() {
        rule.setContent {
            val vm = buildViewModel(rule.activity)
            val navController = rememberNavController()
            AppNavGraph(navController = navController, viewModel = vm)
        }

        rule.onNodeWithTag("todo_row_1").performClick()
        rule.onNodeWithText("Todo Details").assertExists()

        rule.onNodeWithText("Back").performClick()
        rule.onNodeWithText("Todo List").assertExists()
    }
}

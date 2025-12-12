package com.example.todolist.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todolist.presentation.ui.component.TodoItemRow
import com.example.todolist.presentation.viewmodel.TodoUiState
import com.example.todolist.presentation.viewmodel.TodoViewModel

@Composable
fun TodoListScreen(
    viewModel: TodoViewModel,
    onOpenDetails: (todoId: Int) -> Unit
) {
    val state = viewModel.uiState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Todo List")

        when (state) {
            is TodoUiState.Loading -> {
                Text(text = "Loading...")
            }
            is TodoUiState.Error -> {
                Text(text = "Error: ${state.message}")
            }
            is TodoUiState.Content -> {
                LazyColumn {
                    items(state.items) { item ->
                        TodoItemRow(
                            item = item,
                            onClick = { onOpenDetails(item.id) },
                            onToggle = { viewModel.toggleTodo(item.id) }
                        )
                    }
                }
            }
        }
    }
}

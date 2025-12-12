package com.example.todolist.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todolist.presentation.viewmodel.TodoUiState
import com.example.todolist.presentation.viewmodel.TodoViewModel

@Composable
fun TodoDetailScreen(
    viewModel: TodoViewModel,
    todoId: Int,
    onBack: () -> Unit
) {
    val state = viewModel.uiState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Todo Details")

        when (state) {
            is TodoUiState.Loading -> Text(text = "Loading...")
            is TodoUiState.Error -> Text(text = "Error: ${state.message}")
            is TodoUiState.Content -> {
                val item = state.items.firstOrNull { it.id == todoId }
                if (item == null) {
                    Text(text = "Todo not found")
                } else {
                    Text(text = "Title: ${item.title}")
                    Text(text = "Description: ${item.description}")
                    Text(text = "Completed: ${item.isCompleted}")
                }
            }
        }

        Button(onClick = onBack) {
            Text(text = "Back")
        }
    }
}

package com.example.kotlin_6_module.task2.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlin_6_module.task2.domain.NobelPrize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NobelPrizeListScreen(
    onPrizeClick: (NobelPrize) -> Unit,
    viewModel: NobelPrizeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val year by viewModel.selectedYear.collectAsStateWithLifecycle()
    val category by viewModel.selectedCategory.collectAsStateWithLifecycle()

    val categories = listOf(
        "" to "Все категории",
        "physics" to "Physics",
        "chemistry" to "Chemistry",
        "medicine" to "Medicine",
        "literature" to "Literature",
        "peace" to "Peace",
        "economics" to "Economic Sciences"
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Нобелевские лауреаты", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        // Фильтр по году
        OutlinedTextField(
            value = year,
            onValueChange = { viewModel.selectedYear.value = it },
            label = { Text("Год (например, 2023)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Фильтр по категории
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = categories.firstOrNull { it.first == category }?.second ?: "Все категории",
                onValueChange = {},
                readOnly = true,
                label = { Text("Категория") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                categories.forEach { (value, label) ->
                    DropdownMenuItem(
                        text = { Text(label) },
                        onClick = {
                            viewModel.selectedCategory.value = value
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { viewModel.loadPrizes() }, modifier = Modifier.fillMaxWidth()) {
            Text("Применить фильтр")
        }

        Spacer(modifier = Modifier.height(8.dp))

        when (val state = uiState) {
            is NobelUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is NobelUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Ошибка: ${state.message}", color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.loadPrizes() }) { Text("Повторить") }
                    }
                }
            }
            is NobelUiState.Success -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(state.prizes) { prize ->
                        PrizeCard(prize = prize, onClick = { onPrizeClick(prize) })
                    }
                }
            }
        }
    }
}

@Composable
fun PrizeCard(prize: NobelPrize, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("${prize.year} — ${prize.category}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            prize.laureates.forEach { laureate ->
                Text(laureate.fullName, style = MaterialTheme.typography.bodyMedium)
                if (laureate.motivation.isNotBlank()) {
                    Text(
                        laureate.motivation.take(100) + if (laureate.motivation.length > 100) "..." else "",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
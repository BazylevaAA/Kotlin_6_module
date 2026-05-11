package com.example.kotlin_6_module.task6.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrizesScreen(
    viewModel: PrizesViewModel,
    onPrizeClick: (year: String, category: String) -> Unit,
    onFavoritesClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val selectedYear by viewModel.selectedYear.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    var yearInput by remember { mutableStateOf("") }
    var categoryExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Нобелевские премии") },
                actions = {
                    IconButton(onClick = onFavoritesClick) {
                        Icon(Icons.Default.Favorite, contentDescription = "Избранное")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {

            // Фильтры
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Фильтр по году
                OutlinedTextField(
                    value = yearInput,
                    onValueChange = { yearInput = it },
                    label = { Text("Год") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    trailingIcon = {
                        if (yearInput.isNotEmpty()) {
                            TextButton(onClick = {
                                yearInput = ""
                                viewModel.setYear("")
                            }) { Text("X") }
                        }
                    }
                )

                // Применить год
                Button(
                    onClick = { viewModel.setYear(yearInput) },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) { Text("OK") }
            }

            // Фильтр по категории
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)) {
                ExposedDropdownMenuBox(
                    expanded = categoryExpanded,
                    onExpandedChange = { categoryExpanded = it }
                ) {
                    OutlinedTextField(
                        value = if (selectedCategory.isEmpty()) "Все категории" else selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Категория") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = { categoryExpanded = false }
                    ) {
                        viewModel.categories.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(if (cat.isEmpty()) "Все категории" else cat) },
                                onClick = {
                                    viewModel.setCategory(cat)
                                    categoryExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Список
            Box(modifier = Modifier.fillMaxSize()) {
                when (val s = state) {
                    is PrizesState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    is PrizesState.Error -> Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(s.message, color = MaterialTheme.colorScheme.error)
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { viewModel.loadPrizes() }) { Text("Повторить") }
                    }
                    is PrizesState.Success -> LazyColumn(contentPadding = PaddingValues(8.dp)) {
                        items(s.prizes) { prize ->
                            val isFavorite = prize.id in s.favorites
                            PrizeCard(
                                prize = prize,
                                isFavorite = isFavorite,
                                onToggleFavorite = { viewModel.toggleFavorite(prize.id, isFavorite) },
                                onClick = { onPrizeClick(prize.awardYear, prize.category) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PrizeCard(
    prize: com.example.kotlin_6_module.task6.domain.model.NobelPrize,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("${prize.awardYear} — ${prize.category}", style = MaterialTheme.typography.titleMedium)
                Text(prize.fullName, style = MaterialTheme.typography.bodyMedium)
                Text(
                    prize.motivation.take(100) + if (prize.motivation.length > 100) "..." else "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onToggleFavorite) {
                Icon(
                    if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Избранное",
                    tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
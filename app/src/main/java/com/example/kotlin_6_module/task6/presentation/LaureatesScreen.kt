package com.example.kotlin_6_module.task6.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.kotlin_6_module.task6.domain.model.Laureate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaureatesScreen(
    viewModel: LaureatesViewModel,
    year: String,
    category: String,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var selectedLaureate by remember { mutableStateOf<Laureate?>(null) }

    if (selectedLaureate != null) {
        LaureateDetailScreen(
            laureate = selectedLaureate!!,
            year = year,
            category = category,
            onBack = { selectedLaureate = null }
        )
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("$year — $category") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when (val s = state) {
                is LaureatesState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is LaureatesState.Error -> Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(s.message, color = MaterialTheme.colorScheme.error)
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { viewModel.loadLaureates() }) { Text("Повторить") }
                }
                is LaureatesState.Success -> LazyColumn(contentPadding = PaddingValues(8.dp)) {
                    items(s.laureates) { laureate ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .clickable { selectedLaureate = laureate }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (laureate.portraitUrl.isNotEmpty()) {
                                    AsyncImage(
                                        model = laureate.portraitUrl,
                                        contentDescription = laureate.fullName,
                                        modifier = Modifier
                                            .size(56.dp)
                                            .padding(end = 12.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Column {
                                    Text(laureate.fullName, style = MaterialTheme.typography.titleMedium)
                                    Text("Доля: ${laureate.portion}", style = MaterialTheme.typography.bodySmall)
                                    Text(
                                        laureate.motivation.take(80) + if (laureate.motivation.length > 80) "..." else "",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaureateDetailScreen(
    laureate: Laureate,
    year: String,
    category: String,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали лауреата") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                // Фото
                if (laureate.portraitUrl.isNotEmpty()) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        AsyncImage(
                            model = laureate.portraitUrl,
                            contentDescription = laureate.fullName,
                            modifier = Modifier.size(160.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }
            item {
                Text(laureate.fullName, style = MaterialTheme.typography.headlineSmall)
            }
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Год: $year", style = MaterialTheme.typography.bodyLarge)
                        Text("Категория: $category", style = MaterialTheme.typography.bodyLarge)
                        Text("Доля премии: ${laureate.portion}", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Описание", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(4.dp))
                        Text(laureate.motivation, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
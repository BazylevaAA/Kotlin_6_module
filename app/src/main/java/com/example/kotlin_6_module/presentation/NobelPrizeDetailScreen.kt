package com.example.kotlin_6_module.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.kotlin_6_module.domain.NobelPrize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NobelPrizeDetailScreen(
    prize: NobelPrize,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали премии") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("${prize.year} — ${prize.category}", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            prize.laureates.forEach { laureate ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {

                        // Фото
                        if (!laureate.portraitUrl.isNullOrBlank()) {
                            AsyncImage(
                                model = laureate.portraitUrl,
                                contentDescription = laureate.fullName,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        // Полное имя
                        Text(laureate.fullName, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))

                        // Страна
                        if (!laureate.birthCountry.isNullOrBlank()) {
                            Text(
                                "Страна: ${laureate.birthCountry}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }

                        // Полное описание
                        if (laureate.motivation.isNotBlank()) {
                            Text(
                                laureate.motivation,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
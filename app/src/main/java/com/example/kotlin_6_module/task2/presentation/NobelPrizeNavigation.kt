package com.example.kotlin_6_module.task2.presentation

import androidx.compose.runtime.*
import com.example.kotlin_6_module.task2.domain.NobelPrize

@Composable
fun NobelPrizeNavigation() {
    var selectedPrize by remember { mutableStateOf<NobelPrize?>(null) }

    if (selectedPrize == null) {
        NobelPrizeListScreen(
            onPrizeClick = { prize -> selectedPrize = prize }
        )
    } else {
        NobelPrizeDetailScreen(
            prize = selectedPrize!!,
            onBack = { selectedPrize = null }
        )
    }
}
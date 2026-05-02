package com.example.kotlin_6_module.presentation

import androidx.compose.runtime.*
import com.example.kotlin_6_module.domain.NobelPrize

@Composable
fun NobelPrizeNavigation() {
    var selectedPrize by remember { mutableStateOf<NobelPrize?>(null) }

    if (selectedPrize == null) {
        _root_ide_package_.com.example.kotlin_6_module.presentation.NobelPrizeListScreen(
            onPrizeClick = { prize -> selectedPrize = prize }
        )
    } else {
        NobelPrizeDetailScreen(
            prize = selectedPrize!!,
            onBack = { selectedPrize = null }
        )
    }
}
package com.example.kotlin_6_module

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.kotlin_6_module.task3.presentation.Task3Navigation
import com.example.kotlin_6_module.ui.theme.Kotlin_6_moduleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Kotlin_6_moduleTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Task3Navigation()
                }
            }
        }
    }
}
package com.example.kotlin_6_module

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kotlin_6_module.data.PhotoRepositoryImpl
import com.example.kotlin_6_module.data.RetrofitClient
import com.example.kotlin_6_module.domain.GetPhotosUseCase
import com.example.kotlin_6_module.domain.Photo
import com.example.kotlin_6_module.presentation.PhotoDetailScreen
import com.example.kotlin_6_module.presentation.PhotoListScreen
import com.example.kotlin_6_module.presentation.PhotoListViewModel
import com.example.kotlin_6_module.presentation.PhotoListViewModelFactory
import com.example.kotlin_6_module.ui.theme.Kotlin_6_moduleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Kotlin_6_moduleTheme {
                val navController = rememberNavController()

                // Собираем зависимости вручную (без Koin/Hilt)
                val repository = remember { PhotoRepositoryImpl(RetrofitClient.api) }
                val useCase = remember { GetPhotosUseCase(repository) }
                val factory = remember { PhotoListViewModelFactory(useCase) }
                val viewModel: PhotoListViewModel = viewModel(factory = factory)

                // Храним выбранное фото для передачи на экран детализации
                var selectedPhoto: Photo? = remember { null }

                NavHost(
                    navController = navController,
                    startDestination = "list"
                ) {
                    composable("list") {
                        PhotoListScreen(
                            viewModel = viewModel,
                            onPhotoClick = { photo ->
                                selectedPhoto = photo
                                navController.navigate("detail")
                            }
                        )
                    }
                    composable("detail") {
                        selectedPhoto?.let { photo ->
                            PhotoDetailScreen(
                                photo = photo,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
package com.example.kotlin_6_module.task6.presentation

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.kotlin_6_module.task6.data.remote.NobelApiClient
import com.example.kotlin_6_module.task6.data.repository.AuthRepositoryImpl
import com.example.kotlin_6_module.task6.data.repository.NobelRepositoryImpl
import com.example.kotlin_6_module.task6.data.repository.TokenStorage
import com.example.kotlin_6_module.task6.domain.usecase.*

@Composable
fun Task6Navigation() {
    val context = LocalContext.current
    val apiClient = remember { NobelApiClient() }
    val tokenStorage = remember { TokenStorage(context) }
    val authRepository = remember { AuthRepositoryImpl(apiClient, tokenStorage) }
    val nobelRepository = remember { NobelRepositoryImpl(apiClient) }

    val loginUseCase = remember { LoginUseCase(authRepository) }
    val getPrizesUseCase = remember { GetPrizesUseCase(nobelRepository) }
    val getLaureatesUseCase = remember { GetLaureatesUseCase(nobelRepository) }
    val getFavoritesUseCase = remember { GetFavoritesUseCase(nobelRepository) }
    val toggleFavoriteUseCase = remember { ToggleFavoriteUseCase(nobelRepository) }

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            val viewModel = remember {
                LoginViewModel(loginUseCase, authRepository)
            }
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = { token ->
                    navController.navigate("prizes/$token") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = "prizes/{token}",
            arguments = listOf(navArgument("token") { type = NavType.StringType })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val viewModel = remember(token) {
                PrizesViewModel(getPrizesUseCase, getFavoritesUseCase, toggleFavoriteUseCase, token)
            }
            PrizesScreen(
                viewModel = viewModel,
                onPrizeClick = { year, category ->
                    navController.navigate("laureates/$token/$year/$category")
                },
                onFavoritesClick = {
                    navController.navigate("favorites/$token")
                }
            )
        }

        composable(
            route = "laureates/{token}/{year}/{category}",
            arguments = listOf(
                navArgument("token") { type = NavType.StringType },
                navArgument("year") { type = NavType.StringType },
                navArgument("category") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val year = backStackEntry.arguments?.getString("year") ?: ""
            val category = backStackEntry.arguments?.getString("category") ?: ""
            val viewModel = remember(token, year, category) {
                LaureatesViewModel(getLaureatesUseCase, token, year, category)
            }
            LaureatesScreen(
                viewModel = viewModel,
                year = year,
                category = category,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "favorites/{token}",
            arguments = listOf(navArgument("token") { type = NavType.StringType })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val viewModel = remember(token) {
                FavoritesViewModel(getFavoritesUseCase, toggleFavoriteUseCase, token)
            }
            FavoritesScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
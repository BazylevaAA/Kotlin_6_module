package com.example.kotlin_6_module.task3.presentation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Task3Navigation() {
    val context = LocalContext.current
    val viewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory(context))
    val navController = rememberNavController()
    val token by viewModel.token.collectAsState()

    val startDestination = if (token != null) "users" else "login"

    NavHost(navController = navController, startDestination = startDestination) {

        composable("login") {
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate("users") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("users") {
            UsersListScreen(
                viewModel = viewModel,
                onUserClick = { userId ->
                    navController.navigate("user_detail/$userId")
                }
            )
        }

        composable("user_detail/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: return@composable
            UserDetailScreen(
                userId = userId,
                viewModel = viewModel,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
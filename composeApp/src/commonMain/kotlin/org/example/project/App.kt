package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import org.example.project.ui.ShopListsScreen
import org.example.project.ui.WelcomeScreen

import org.koin.compose.KoinContext

@Composable
fun App() {
    val navController = rememberNavController()
    MaterialTheme {
        KoinContext {
            NavHost(
                navController = navController,
                startDestination = "welcome"
            ) {
                composable(route = "welcome") {
                    WelcomeScreen(navController)
                }
                composable(route = "allshoplists/{key}") {stackEntry ->
                    val key = stackEntry.arguments?.getString("key")
                    ShopListsScreen(key?:"Ошибка")
                }
            }
        }
    }
}
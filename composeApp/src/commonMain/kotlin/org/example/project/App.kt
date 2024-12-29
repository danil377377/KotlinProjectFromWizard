package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mykmpapplicationfromtemplate.viewModel.PurchasesViewModel
import org.jetbrains.compose.resources.painterResource

import kotlinprojectfromwizard.composeapp.generated.resources.Res
import kotlinprojectfromwizard.composeapp.generated.resources.compose_multiplatform
import org.example.project.ui.WelkomeScreen
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    MaterialTheme {
        KoinContext {
            NavHost(
                navController = rememberNavController(),
                startDestination = "home"
            ) {
                composable(route = "home") {
//                    val viewModel = koinViewModel<PurchasesViewModel>()
//                    val text = viewModel.key.collectAsState()
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        TextButton(onClick = {viewModel.getKey()}){
//                           Text(text = text.value)
//                        }
//                    }
                    WelkomeScreen()
                }
            }
        }
    }
}
package org.example.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mykmpapplicationfromtemplate.viewModel.WelcomeViewModel
import org.example.project.viewModel.WelcomeScreenAction
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WelcomeScreen(navController: NavController) {
    val myButtonColors = ButtonDefaults.filledTonalButtonColors(
        containerColor = Color.Red,
        contentColor = Color.Red,
        disabledContainerColor = Color.LightGray,
        disabledContentColor = Color.White
    )
    val viewModel = koinViewModel<WelcomeViewModel>()
    val savedKey = viewModel.key.collectAsState()
    val newKey = viewModel.insertedKey.collectAsState()
    val isInputCorrect = viewModel.isInputCorrect.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val isGetKeyError = viewModel.isGetKeyError.collectAsState()
    val isGetShopListsError = viewModel.isGetShopListsError.collectAsState()
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Добро пожаловать!", fontSize = 25.sp, modifier = Modifier.padding(top = 32.dp))
        Column(Modifier.fillMaxWidth().padding(top = 32.dp)) {
            if (isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            } else {
                Text(
                    "Ваш ключ: ${savedKey.value}",
                    fontSize = 20.sp,
                    modifier = Modifier.background(Color.LightGray)
                )
            }
            if (isGetKeyError.value) {
                Button({ viewModel.getKey() }) {
                    Text("Попробовать ещё раз")
                }
            } else {
                FilledTonalButton(
                    {
                        navController.navigate("allshoplists/${savedKey.value}")
                    },
                    colors = myButtonColors,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = true
                ) { Text("Перейти к вашим спискам покупок") }
            }
        }
        Column(Modifier.fillMaxWidth().padding(top = 16.dp)) {
            OutlinedTextField(
                value = newKey.value,
                onValueChange = { viewModel.dispatch(WelcomeScreenAction.KeyInputChanged(it)) },
                placeholder = {
                    Text(
                        "Введите 6-ти значный ключ"
                    )
                })
            if (!isGetShopListsError.value.isBlank()) {
                Text(isGetShopListsError.value, color = Color.Red)
            }
            FilledTonalButton(
                {
                    viewModel.dispatch(WelcomeScreenAction.ContinueWithNewKey)
                    navController.navigate("allshoplists/${newKey.value}")
                },
                colors = myButtonColors,
                modifier = Modifier.fillMaxWidth(),
                enabled = isInputCorrect.value
            ) { Text("Перейти к спискам покупок по ключу") }

        }
    }
}
package org.example.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.TextButton
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun WelkomeScreen() {
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Добро пожаловать!", fontSize = 25.sp, modifier = Modifier.padding(top = 32.dp))
        Column(Modifier.fillMaxWidth().padding(top = 32.dp)) {
            Text("Ваш ключ: 3223СР", fontSize = 20.sp, modifier = Modifier.background(Color.LightGray))
            TextButton({}){ Text("Перейти к вашим спискам покупок") }
        }
        Column(Modifier.fillMaxWidth().padding(top = 16.dp)){
            TextField("Введите 6-ти значный ключ", {})
            TextButton({}){ Text("Перейти к спискам покупок по ключу") }
        }
    }
}
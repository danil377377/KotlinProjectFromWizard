package org.example.project.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.example.project.viewModel.AllShopListsAction
import org.example.project.viewModel.AllShopListsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ShopListsScreen(key:String, navController: NavController) {
    val viewModel = koinViewModel<AllShopListsViewModel>()
    val shoplists = viewModel.shopLists.collectAsState()
    val isError = viewModel.isGetShopListsError.collectAsState()
    val isLoading=viewModel.isLoading.collectAsState()
    val nameInput = viewModel.insertedName.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var chosenShoplist by remember { mutableStateOf("") }

    val addButtonColors = ButtonDefaults.filledTonalButtonColors(
        containerColor = Color.Blue,
        contentColor = Color.Blue,
        disabledContainerColor = Color.LightGray,
        disabledContentColor = Color.White
    )
    val removeButtonColors = ButtonDefaults.filledTonalButtonColors(
        containerColor = Color.Red,
        contentColor = Color.Red,
        disabledContainerColor = Color.LightGray,
        disabledContentColor = Color.White
    )
    LaunchedEffect(key) {
        viewModel.observeShoplists(key)
    }
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FilledTonalButton(
            {
                navController.popBackStack()
            },){
            Text("Назад")
        }
        Text("Ключ: $key", fontSize = 25.sp, modifier = Modifier.padding(top = 32.dp))
        OutlinedTextField(
            value = nameInput.value,
            onValueChange = { viewModel.dispatch(AllShopListsAction.NameInputChanged(it)) },
            placeholder = {
                Text(
                    "Введите имя списка на английском"
                )
            })
        FilledTonalButton(
            {
viewModel.dispatch(AllShopListsAction.CreateShoplist(key))
            },
            colors = addButtonColors,
            modifier = Modifier.fillMaxWidth(),
            enabled = !nameInput.value.isBlank()
        ) { Text("Добавить список покупок") }
        if (isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        } else {
            if (isError.value.isBlank()) {
                shoplists.value.forEach {
                    Row(modifier = Modifier.padding(16.dp).fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween ) {
                        Column(modifier = Modifier.weight(1f)) {
                        Text(it.name, fontSize = 20.sp)
                        Text(it.created, color = Color.LightGray)}

                    }
                    Box(Modifier.fillMaxWidth()) {
                        Row(Modifier.align(Alignment.CenterStart)) {
                            Button(
                                onClick = {navController.navigate("shoppinglist/${it.id}/${it.name}")},
                            ) {
                                Text("Перейти")
                            }
                        }
                        Row(Modifier.align(Alignment.CenterEnd)) {
                            FilledTonalButton(
                                onClick = {chosenShoplist = it.id.toString()
                                          showDialog = true},
                                colors = removeButtonColors
                            ) {
                                Text("Удалить")
                            }
                        }
                    }
                    HorizontalDivider(modifier = Modifier.fillMaxWidth())
                }
            } else Text(isError.value, color = Color.Red)
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Подтверждение удаления") },
            text = { Text("Вы действительно хотите удалить список покупок?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.dispatch(AllShopListsAction.RemoveShoplist(chosenShoplist, key))
                    showDialog = false
                }) {
                    Text("Удалить")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }
}

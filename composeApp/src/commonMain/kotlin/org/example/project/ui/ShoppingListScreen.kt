package org.example.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.viewModel.ShoppingListAction
import org.example.project.viewModel.ShoppingListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ShoppingListScreen(listId: String, listName: String) {
    val viewModel = koinViewModel<ShoppingListViewModel>()
    val items = viewModel.items.collectAsState()
    val isError = viewModel.isGetItemsError.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val nameInput = viewModel.insertedName.collectAsState()
    val quntityInput = viewModel.insertedQuantity.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var chosenItem by remember { mutableStateOf("") }


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
    LaunchedEffect(listId) {
        viewModel.observeItems(listId)
    }
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(listName, fontSize = 25.sp, modifier = Modifier.padding(top = 32.dp))
        OutlinedTextField(
            value = nameInput.value,
            onValueChange = { viewModel.dispatch(ShoppingListAction.NameInputChanged(it)) },
            placeholder = {
                Text(
                    "Введите название покупки на английском"
                )
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            )
        )
        OutlinedTextField(
            modifier = Modifier.padding(8.dp),
            value = quntityInput.value,
            onValueChange = { viewModel.dispatch(ShoppingListAction.QuantityInputChanged(it)) },
            placeholder = {
                Text(
                    "Введите количество товара"
                )
            }, keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            )
        )
        FilledTonalButton(
            {
                viewModel.dispatch(
                    ShoppingListAction.AddToShoppingList(
                        listId,
                        nameInput.value,
                        quntityInput.value
                    )
                )
            },
            colors = addButtonColors,
            modifier = Modifier.fillMaxWidth(),
            enabled = !nameInput.value.isBlank() && !quntityInput.value.isBlank() && quntityInput.value.toDoubleOrNull() != null
        ) { Text("Добавить покупку") }
        if (isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        } else {
            if (isError.value.isBlank()) {
                items.value.forEach {
                    val color = if (it.is_crossed) Color.LightGray else Color(0xFFADD8E6)
                    Box(modifier = Modifier.padding(vertical = 8.dp)) {
                        Column(Modifier.background(color)) {
                            Row(
                                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(modifier = Modifier.weight(1f)) {
                                    Text(it.name, fontSize = 28.sp, modifier = Modifier.weight(1f),
                                        style = if (it.is_crossed) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle.Default)
                                    Text(
                                        "Количество:\n${it.created}",
                                        color = Color.Gray,
                                        fontSize = 22.sp,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }

                            }
                            Box(Modifier.fillMaxWidth()) {
                                Row(Modifier.align(Alignment.CenterStart)) {
                                    Button(
                                        onClick = {},
                                    ) {
                                        Text("Вычеркнуть/Восстановить")
                                    }
                                }
                                Row(Modifier.align(Alignment.CenterEnd)) {
                                    FilledTonalButton(
                                        onClick = {
                                            chosenItem = it.id.toString()
                                            showDialog = true
                                        },
                                        colors = removeButtonColors
                                    ) {
                                        Text("Удалить")
                                    }
                                }
                            }
                            HorizontalDivider(modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            } else Text(isError.value, color = Color.Red)
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Подтверждение удаления") },
            text = { Text("Вы действительно хотите удалить покупку?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.dispatch(
                        ShoppingListAction.RemoveFromShoppingList(
                            listId,
                            chosenItem
                        )
                    )
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
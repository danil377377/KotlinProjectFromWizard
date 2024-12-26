package com.example.mykmpapplicationfromtemplate.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.data.network.KtorClient

class PurchasesViewModel() : ViewModel() {
    private val _key = MutableStateFlow<String>("Привеееееееееет")
    val key = _key.asStateFlow()
    val ktorClient = KtorClient()
    fun getKey() {
        viewModelScope.launch(Dispatchers.IO) {
            _key.value = ktorClient.getAutentificationKey()
        }
    }
}
package org.example.project.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.network.model.GetAllShopListsResponse
import org.example.project.data.network.model.Item
import org.example.project.data.network.model.ShoppingListResponse
import org.example.project.domain.ShoppingListRepository

class ShoppingListViewModel(val repository: ShoppingListRepository) : ViewModel() {

    private val _isGetItemsError = MutableStateFlow<String>("")
    val isGetItemsError = _isGetItemsError.asStateFlow()
    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items = _items.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private val _insertedName = MutableStateFlow<String>("")
    val insertedName = _insertedName.asStateFlow()
    private val _insertedQuantity = MutableStateFlow<String>("")
    val insertedQuantity = _insertedQuantity.asStateFlow()

    fun dispatch(action: ShoppingListAction) {
        viewModelScope.launch {
            when (action) {
                is ShoppingListAction.NameInputChanged -> {
                    _insertedName.update { action.name }
                }
                is ShoppingListAction.AddToShoppingList -> addToShoplist(action.listId,action.name,action.n)
                is ShoppingListAction.RemoveFromShoppingList -> removeFromShoplist(action.listId, action.itemId)
                is ShoppingListAction.QuantityInputChanged -> {
                _insertedQuantity.update { action.quantity }
            }

                is ShoppingListAction.CrossItem -> crossItem(action.itemId, action.listId)
            }
        }
    }

    fun getItems(listId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val items = repository.getShoppingList(listId)
            if (items.resultCode == 200) {
                _items.value = (items as ShoppingListResponse).item_list.reversed()
                _isGetItemsError.value = ""
            } else {
                _isGetItemsError.value = when (items.resultCode) {
                    -1 -> "ERROR:No internet connection"
                    -3 -> "ERROR:Request timeout after 3 seconds"
                    else -> "Unknown Error"
                }
            }
            _isLoading.value = false
        }
    }

    fun observeItems(listId: String) {
        viewModelScope.launch {
            getItemsFlow(listId)
                .collect {
                    _items.value = it
                    _isLoading.value = false
                }
        }
    }

    private fun getItemsFlow(listId: String) = flow {
        while (true) {
            val response = repository.getShoppingList(listId)
            if (response.resultCode == 200) {
                emit((response as ShoppingListResponse).item_list.reversed())
                _isGetItemsError.value = ""
            } else {
                _isGetItemsError.value = when (response.resultCode) {
                    -1 -> "ERROR:No internet connection"
                    -3 -> "ERROR:Request timeout after 3 seconds"
                    else -> "Unknown Error"
                }
                emit(emptyList())
            }
            delay(5000)
        }
    }.onStart { _isLoading.value = true }

    fun addToShoplist(listId:String, name:String, n:String){
        viewModelScope.launch {
            _isLoading.value = true
            val response = repository.addToShoppingList(listId, name, n)
            if (response.resultCode != 200) {
                _isGetItemsError.value = when (response.resultCode) {
                    -1 -> "ERROR:No internet connection"
                    -3 -> "ERROR:Request timeout after 3 seconds"
                    else -> "Unknown Error"
                }
            }
            _isLoading.value = false
            getItems(listId)
        }
    }

    fun removeFromShoplist(listId:String, itemId:String){
        viewModelScope.launch {
            _isLoading.value = true
            val response = repository.removeFromList(listId, itemId)
            if (response.resultCode != 200) {
                _isGetItemsError.value = when (response.resultCode) {
                    -1 -> "ERROR:No internet connection"
                    -3 -> "ERROR:Request timeout after 3 seconds"
                    else -> "Unknown Error"
                }
            }
            _isLoading.value = false
            getItems(listId)
        }
    }

    fun crossItem(itemId:String, listId: String){
        viewModelScope.launch {
            _isLoading.value=true
            val response = repository.crossItem(itemId)
            if(response.resultCode!= 200){
                _isGetItemsError.value = when (response.resultCode) {
                    -1 -> "ERROR:No internet connection"
                    -3 -> "ERROR:Request timeout after 3 seconds"
                    else -> "Unknown Error"
                }
            }
            _isLoading.value = false
            getItems(listId)
        }
    }

}
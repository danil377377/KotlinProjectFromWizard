package org.example.project.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykmpapplicationfromtemplate.domain.WelcomeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.network.model.GetAllShopListsResponse
import org.example.project.data.network.model.ShopList
import org.example.project.domain.AllShoplistsRepository

class AllShopListsViewModel(
    val welcomeRepository: WelcomeRepository,
    val allShoplistsRepository: AllShoplistsRepository,
) : ViewModel() {
    private val _isGetShopListsError = MutableStateFlow<String>("")
    val isGetShopListsError = _isGetShopListsError.asStateFlow()
    private val _shopLists = MutableStateFlow<List<ShopList>>(emptyList())
    val shopLists = _shopLists.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private val _insertedName = MutableStateFlow<String>("")
    val insertedName = _insertedName.asStateFlow()


    fun dispatch(action: AllShopListsAction) {
        viewModelScope.launch {
            when (action) {
                is AllShopListsAction.NameInputChanged -> {
                    _insertedName.update { action.key }
                }

                is AllShopListsAction.CreateShoplist -> {
                    val response = allShoplistsRepository.createShoplist(action.key, insertedName.value)
                    if (response.resultCode == 200) {
                        _isGetShopListsError.value = ""
                    } else {
                        _isGetShopListsError.value = when (response.resultCode) {
                            -1 -> "ERROR:No internet connection"
                            -3 -> "ERROR:Request timeout after 3 seconds"
                            else -> ""
                        }
                    }
                    getShoplists(action.key)
                    _insertedName.value=""
                }
            }
        }
    }

    fun getShoplists(insertedKey: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val shopLists = welcomeRepository.getAllShopLists(insertedKey)
            if (shopLists.resultCode == 200) {
                _shopLists.value = (shopLists as GetAllShopListsResponse).shop_list.reversed()
                _isGetShopListsError.value = ""
            } else {
                _isGetShopListsError.value = when (shopLists.resultCode) {
                    -1 -> "ERROR:No internet connection"
                    -3 -> "ERROR:Request timeout after 3 seconds"
                    else -> ""
                }
            }
            _isLoading.value = false
        }
    }

    fun observeShoplists(insertedKey: String) {
        viewModelScope.launch {
            getShopListsFlow(insertedKey)
                .collect{
                    _shopLists.value = it
                    _isLoading.value = false
                }
        }
    }

    private fun getShopListsFlow(insertedKey: String) = flow {
        while (true) {
            val shopListsResponse = welcomeRepository.getAllShopLists(insertedKey)
            if (shopListsResponse.resultCode == 200) {
                emit((shopListsResponse as GetAllShopListsResponse).shop_list.reversed())
                _isGetShopListsError.value = ""
            } else {
                _isGetShopListsError.value = when (shopListsResponse.resultCode) {
                    -1 -> "ERROR:No internet connection"
                    -3 -> "ERROR:Request timeout after 3 seconds"
                    else -> ""
                }
                emit(emptyList())
            }
            delay(5000)
        }
    }
        .onStart { _isLoading.value = true }
}


package com.example.mykmpapplicationfromtemplate.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykmpapplicationfromtemplate.domain.WelcomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.Preferences.PreferencesImpl
import org.example.project.data.network.model.GenerateKeyResponse
import org.example.project.viewModel.WelcomeScreenAction
import org.koin.core.component.KoinComponent


class WelcomeViewModel(val repository: WelcomeRepository, val pref :PreferencesImpl) : ViewModel(), KoinComponent {
    private val _key = MutableStateFlow<String>("")
    val key = _key.asStateFlow()
    private val _insertedKey = MutableStateFlow<String>("")
    val insertedKey = _insertedKey.asStateFlow()
    private val _isInpurCorrect = MutableStateFlow(false)
    val isInputCorrect = _isInpurCorrect.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private val _isGetKeyError = MutableStateFlow(true)
    val isGetKeyError = _isGetKeyError.asStateFlow()
    private val _isGetShopListsError = MutableStateFlow<String>("")
    val isGetShopListsError = _isGetShopListsError.asStateFlow()
    init {
        getKey()
    }
    fun getKey() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            val savedKey = pref.getString(AUTENTIFICATION_KEY)
            if (!savedKey.isNullOrBlank()) {
                _key.value = savedKey
                _isGetKeyError.value = false
            } else {
                val key =  repository.getAutentificationKey() as GenerateKeyResponse
                if(key.resultCode==200&& !key.data.isNullOrBlank()) {
                    pref.putString(AUTENTIFICATION_KEY, key.data)
                    _key.value = key.data
                    _isGetKeyError.value = false
                } else{
                    _isGetKeyError.value = true
                    when(key.resultCode){
                        -1 -> _key.value = "ERROR:No internet connection"
                        -3 -> _key.value = "ERROR:Request timeout after 3 seconds"
                    }
                }
            }
            _isLoading.value = false
        }
    }

    fun dispatch(action: WelcomeScreenAction){
        viewModelScope.launch {
            when(action){
                WelcomeScreenAction.ContinueWithNewKey -> {
                  val shopLists = repository.getAllShopLists(insertedKey.value)
                    if(shopLists.resultCode==200) {
                        _isGetShopListsError.value = ""
                    } else{
                        _isGetShopListsError.value = when(shopLists.resultCode){
                            -1 -> "ERROR:No internet connection"
                            -3 -> "ERROR:Request timeout after 3 seconds"
                            else ->""
                        }
                    }
                }
                WelcomeScreenAction.ContinueWithSavedKey -> TODO()
                is WelcomeScreenAction.KeyInputChanged -> {
                    _insertedKey.update { action.key }
                    if(insertedKey.value.length == 6){
                        _isInpurCorrect.update{true}
                    } else{
                        _isInpurCorrect.update{false}
                    }
                }
            }
        }
    }
    companion object {
        const val AUTENTIFICATION_KEY = "autentification_key"
    }
}
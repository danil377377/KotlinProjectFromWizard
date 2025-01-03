package com.example.mykmpapplicationfromtemplate.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykmpapplicationfromtemplate.domain.PurchasesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.Preferences.PreferencesImpl
import org.example.project.data.network.PurchasesDataSource
import org.example.project.viewModel.WelkomeScreenAction
import org.koin.core.component.KoinComponent


class PurchasesViewModel(val repository: PurchasesRepository,val pref :PreferencesImpl) : ViewModel(), KoinComponent {
    private val _key = MutableStateFlow<String>("")
    val key = _key.asStateFlow()
    private val _insertedKey = MutableStateFlow<String>("")
    val insertedKey = _insertedKey.asStateFlow()
    private val _isInpurCorrect = MutableStateFlow(false)
    val isInputCorrect = _isInpurCorrect.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private val _isError = MutableStateFlow(true)
    val isError = _isError.asStateFlow()
    init {
        getKey()
    }
    fun getKey() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            val savedKey = pref.getString(AUTENTIFICATION_KEY)
            if (!savedKey.isNullOrBlank()) {
                _key.value = savedKey
                _isError.value = false
            } else {
                val key =  repository.getAutentificationKey()
                if(!key.data.isNullOrBlank()) {
                    pref.putString(AUTENTIFICATION_KEY, key.data)
                    _key.value = key.data
                    _isError.value = false
                } else{
                    _key.value= key.errorMessage?:"err"
                    _isError.value = true

                }
            }
            _isLoading.value = false
        }
    }

    fun dispatch(action: WelkomeScreenAction){
        viewModelScope.launch {
            when(action){
                WelkomeScreenAction.ContinueWithNewKey -> TODO()
                WelkomeScreenAction.ContinueWithSavedKey -> TODO()
                is WelkomeScreenAction.KeyInputChanged -> {
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
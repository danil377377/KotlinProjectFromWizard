package com.example.mykmpapplicationfromtemplate.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.Preferences.PreferencesImpl
import org.example.project.data.network.PurchasesDataSource
import org.koin.core.component.KoinComponent


class PurchasesViewModel(val ktorClient:PurchasesDataSource,val pref :PreferencesImpl) : ViewModel(), KoinComponent {
    private val _key = MutableStateFlow<String>("")
    val key = _key.asStateFlow()
    init {
        getKey()
    }
    fun getKey() {
        viewModelScope.launch(Dispatchers.IO) {
            val savedKey = pref.getString(AUTENTIFICATION_KEY)
            if (!savedKey.isNullOrBlank()) {
                _key.value = savedKey
            } else {
                val key =  ktorClient.getAutentificationKey()
                pref.putString(AUTENTIFICATION_KEY, key)
                _key.value = key
            }
        }
    }
    companion object {
        const val AUTENTIFICATION_KEY = "autentification_key"
    }
}
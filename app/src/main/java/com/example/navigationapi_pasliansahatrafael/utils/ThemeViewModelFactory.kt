package com.example.navigationapi_pasliansahatrafael.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navigationapi_pasliansahatrafael.ui.viewmodel.MainViewModel
import com.example.navigationapi_pasliansahatrafael.setting.SettingPreferences

class ThemeViewModelFactory (private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
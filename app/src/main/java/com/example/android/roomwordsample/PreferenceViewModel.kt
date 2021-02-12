package com.example.android.roomwordsample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PreferenceViewModel(private val preferencesManager: PreferencesManager) : ViewModel() {

    val preferencesFlow = preferencesManager.preferencesFlow.asLiveData()

    fun onDarkModeClick(themeMode: UiTheme) = viewModelScope.launch {
        preferencesManager.updateEnableDark(themeMode)
    }

}

class PreferenceViewModelFactory(private val preferencesManager: PreferencesManager) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PreferenceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PreferenceViewModel(preferencesManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

package com.gojigo.githubser.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gojigo.githubser.data.di.Injection

class ThemeViewModelFactory(val settingPreferences: SettingPreferences) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(settingPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ThemeViewModelFactory? = null

        fun getInstance(
            context: Context
        ): ThemeViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ThemeViewModelFactory(Injection.provideSettingPreferences(context))
            }.also { instance = it }
    }
}
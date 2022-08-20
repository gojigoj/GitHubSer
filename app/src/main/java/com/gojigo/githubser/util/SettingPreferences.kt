package com.gojigo.githubser.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    companion object {
        private const val THEME_SETTING = "themeSetting"

        private val THEME_KEY = booleanPreferencesKey(THEME_SETTING)

        @Volatile
        private var instance: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences =
            instance ?: synchronized(this) {
                instance ?: SettingPreferences(dataStore)
            }.also { instance = it }
    }
}
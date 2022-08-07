package com.gojigo.githubser.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.gojigo.githubser.data.Repository
import com.gojigo.githubser.data.local.room.UserDatabase
import com.gojigo.githubser.data.remote.ApiConfig
import com.gojigo.githubser.util.SettingPreferences

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Settings")

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.retrofit
        val database = UserDatabase.getInstance(context)
        val dao = database.savedUsedDao()
        return Repository.getInstance(apiService, dao)
    }

    fun provideSettingPreferences(context: Context): SettingPreferences {
        return SettingPreferences.getInstance(context.applicationContext.dataStore)
    }
}
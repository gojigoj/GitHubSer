package com.gojigo.githubser.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gojigo.githubser.data.Repository
import com.gojigo.githubser.data.di.Injection
import com.gojigo.githubser.ui.main.view.MainViewModel
import com.gojigo.githubser.ui.saveduser.view.SavedUserViewModel
import com.gojigo.githubser.ui.userdetail.view.UserDetailViewModel

class ViewModelFactory private constructor(
    val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> return MainViewModel(
                repository
            ) as T
            modelClass.isAssignableFrom(UserDetailViewModel::class.java) -> return UserDetailViewModel(
                repository
            ) as T
            modelClass.isAssignableFrom(SavedUserViewModel::class.java) -> return SavedUserViewModel(
                repository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(
            context: Context
        ): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideRepository(context)
                )
            }.also { instance = it }
    }
}
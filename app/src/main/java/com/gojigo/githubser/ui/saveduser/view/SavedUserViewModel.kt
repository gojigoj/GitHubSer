package com.gojigo.githubser.ui.saveduser.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gojigo.githubser.data.Repository
import com.gojigo.githubser.data.local.entity.UserEntity

class SavedUserViewModel(private val repository: Repository) : ViewModel() {

    fun getAllUser(): LiveData<List<UserEntity>> {
        return repository.getAllSavedUser()
    }
}
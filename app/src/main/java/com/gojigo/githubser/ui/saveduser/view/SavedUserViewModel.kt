package com.gojigo.githubser.ui.saveduser.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gojigo.githubser.data.Repository
import com.gojigo.githubser.data.local.entity.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SavedUserViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun getAllUser(): LiveData<List<UserEntity>> {
        return repository.getAllSavedUser()
    }
}
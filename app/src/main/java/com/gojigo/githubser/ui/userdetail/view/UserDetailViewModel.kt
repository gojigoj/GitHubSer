package com.gojigo.githubser.ui.userdetail.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gojigo.githubser.data.Repository
import com.gojigo.githubser.data.local.entity.UserEntity
import kotlinx.coroutines.launch

class UserDetailViewModel(private val repository: Repository) : ViewModel() {

    fun getUserDetail(userName: String) = repository.getUserDetail(userName)

    fun getUserFollowers(userName: String) = repository.getUserFollowers(userName)

    fun getUserFollowing(userName: String) = repository.getUserFollowing(userName)

    fun saveUser(user: UserEntity) {
        viewModelScope.launch {
            repository.savedUser(user)
        }
    }

    fun unSaveUser(user: UserEntity) {
        viewModelScope.launch {
            repository.deleteUser(user)
        }
    }

    fun isUserSaved(idUser: Int) = repository.getUserById(idUser)
}
package com.gojigo.githubser.ui.main.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gojigo.githubser.data.Repository
import com.gojigo.githubser.data.local.entity.UserEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) :
    ViewModel() {

    private val _isLoading = MutableLiveData(true)
    val isLoading get() = _isLoading

    val _listUser = MutableLiveData<List<UserEntity>>()

    init {
        viewModelScope.launch {
            delay(300)
            isLoading.value = false
        }
    }

    fun searchUser(userName: String) = repository.searchUser(userName)

}
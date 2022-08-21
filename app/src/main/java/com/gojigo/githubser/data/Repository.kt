package com.gojigo.githubser.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.gojigo.githubser.data.local.entity.UserEntity
import com.gojigo.githubser.data.local.room.SavedUserDao
import com.gojigo.githubser.data.remote.ApiService
import com.gojigo.githubser.data.response.UserDetailResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val apiService: ApiService,
    private val savedUserDao: SavedUserDao
) {

    fun searchUser(userName: String): LiveData<Result<List<UserEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.searchUsers(userName)
            val listUsers = response.items
            emit(Result.Success(listUsers))
        } catch (e: Exception) {
            Log.e(TAG, "searchUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserDetail(userName: String): LiveData<Result<UserDetailResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserDetail(userName)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "searchUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserFollowers(userName: String): LiveData<Result<List<UserEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserFollowers(userName)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "searchUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserFollowing(userName: String): LiveData<Result<List<UserEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserFollowing(userName)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "searchUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getAllSavedUser(): LiveData<List<UserEntity>> {
        return savedUserDao.getSavedUsers()
    }

    fun getUserById(idUser: Int): LiveData<List<UserEntity>> {
        return savedUserDao.getUserById(idUser)
    }

    suspend fun savedUser(user: UserEntity) {
        savedUserDao.insert(user)
    }

    suspend fun deleteUser(user: UserEntity) {
        savedUserDao.delete(user)
    }

    companion object {
        private val TAG = Repository::class.java.simpleName
    }

}
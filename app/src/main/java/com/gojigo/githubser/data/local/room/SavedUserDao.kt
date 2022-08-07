package com.gojigo.githubser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gojigo.githubser.data.local.entity.UserEntity

@Dao
interface SavedUserDao {

    @Query("SELECT * FROM saved_user ORDER BY created_at DESC")
    fun getSavedUsers(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(savedUser: UserEntity)

    @Delete
    suspend fun delete(savedUser: UserEntity)

    @Query("SELECT * FROM saved_user WHERE id = :idUser")
    fun getUserById(idUser: Int): LiveData<List<UserEntity>>
}
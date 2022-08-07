package com.gojigo.githubser.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "saved_user")
data class UserEntity(

    @field:ColumnInfo("id")
    @field:PrimaryKey
    @field:SerializedName("id")
    val id: Int,

    @field:ColumnInfo("login")
    @field:SerializedName("login")
    val login: String? = "-",

    @field:SerializedName("avatar_url")
    @field:ColumnInfo("avatar_url")
    val avatarUrl: String? = null,

    @field:ColumnInfo("created_at")
    val createdAt: String?,

) : Parcelable
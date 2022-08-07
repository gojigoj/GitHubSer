package com.gojigo.githubser.data.response

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(

    @field:SerializedName("created_at")
    val createdAt: String? = "",

    @field:SerializedName("login")
    val login: String? = "",

    @field:SerializedName("updated_at")
    val updatedAt: String? = "",

    @field:SerializedName("company")
    val company: String? = "-",

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("public_repos")
    val publicRepos: Int? = 0,

    @field:SerializedName("followers")
    val followers: Int? = 0,

    @field:SerializedName("following")
    val following: Int? = 0,

    @field:SerializedName("name")
    val name: String? = "-",

    @field:SerializedName("location")
    val location: String? = "-",

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = "",
)

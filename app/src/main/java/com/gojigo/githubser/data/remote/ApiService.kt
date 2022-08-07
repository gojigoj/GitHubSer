package com.gojigo.githubser.data.remote

import com.gojigo.githubser.data.local.entity.UserEntity
import com.gojigo.githubser.data.response.SearchUserResponse
import com.gojigo.githubser.data.response.UserDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/search/users")
    suspend fun searchUsers(
        @Query("q") userName: String
    ): SearchUserResponse

    @GET("/users/{username}")
    suspend fun getUserDetail(
        @Path("username") userName: String
    ): UserDetailResponse

    @GET("/users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username") userName: String
    ): List<UserEntity>

    @GET("/users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") userName: String
    ): List<UserEntity>

}
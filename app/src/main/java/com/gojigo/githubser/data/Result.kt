package com.gojigo.githubser.data

sealed class Result<out R> private constructor() {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val errorResponse: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
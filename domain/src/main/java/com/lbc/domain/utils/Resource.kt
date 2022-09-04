package com.lbc.domain.utils

sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Error<out T : Any>(val errorMessage: String, val data: T? = null) : Resource<T>()
}
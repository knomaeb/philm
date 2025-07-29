package com.example.philm.core.utils

sealed class Resource<out T>(val data: T? = null, val statusMessage: String? = null) {

    class Success<out T>(data: T) : Resource<T>(data = data)

    object Loading : Resource<Nothing>()

    class Error<T>(message: String, data: T? = null) : Resource<T>(statusMessage = message)

    class Empty<T> : Resource<T>()

}
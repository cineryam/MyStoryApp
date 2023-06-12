package com.example.mystoryapp.data

sealed class Outcome<out R> private constructor() {
    data class Success<out T>(val data: T) : Outcome<T>()
    data class Error(val error: String) : Outcome<Nothing>()
    object Loading : Outcome<Nothing>()
}

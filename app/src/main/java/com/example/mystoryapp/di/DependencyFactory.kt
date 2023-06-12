package com.example.mystoryapp.di

import android.content.Context
import com.example.mystoryapp.data.api.ApiConfig
import com.example.mystoryapp.data.StoryRepo

object DependencyFactory {
    fun provideRepo(context: Context): StoryRepo {
        val apiService = ApiConfig.getApiService(context)
        return StoryRepo(apiService)
    }
}
package com.example.mystoryapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.mystoryapp.data.api.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepo(private val apiService: ApiService) {
    fun getStories(): LiveData<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }

    fun getStoriesWithLocation(): LiveData<Outcome<StoriesResponse>> = liveData {
        emit(Outcome.Loading)
        try {
            val response = apiService.getStoriesWithLocation(1)
            emit(Outcome.Success(response))
        } catch (e: Exception) {
            Log.d("StoriesViewModel", "getStoriesWithLocation: ${e.message.toString()} ")
            emit(Outcome.Error(e.message.toString()))
        }
    }

    fun postStory(file: MultipartBody.Part, description: RequestBody): LiveData<Outcome<PostStoryResponse>> = liveData {
        emit(Outcome.Loading)
        try {
            val response = apiService.postStory(file, description)
            emit(Outcome.Success(response))
        } catch (e: Exception) {
            Log.e("AddStoryViewModel", "postStory: ${e.message.toString()}")
            emit(Outcome.Error(e.message.toString()))
        }
    }

    fun postRegister(name: String, email: String, password: String): LiveData<Outcome<RegisterResponse>> = liveData {
        emit(Outcome.Loading)
        try {
            val response = apiService.postRegister(name, email, password)
            emit(Outcome.Success(response))
        } catch (e: Exception) {
            Log.e("RegisterViewModel", "postRegister: ${e.message.toString()}")
            emit(Outcome.Error(e.message.toString()))
        }
    }

    fun postLogin(email: String, password: String): LiveData<Outcome<LoginResponse>> = liveData {
        emit(Outcome.Loading)
        try {
            val response = apiService.postLogin(email, password)
            emit(Outcome.Success(response))
        } catch (e: Exception) {
            Log.e("LoginViewModel", "postLogin: ${e.message.toString()}")
            emit(Outcome.Error(e.message.toString()))
        }
    }
}
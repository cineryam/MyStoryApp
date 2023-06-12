package com.example.mystoryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mystoryapp.data.StoryRepo
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val storyRepos: StoryRepo): ViewModel() {
    fun postStory(file: MultipartBody.Part, description: RequestBody) = storyRepos.postStory(file, description)
}
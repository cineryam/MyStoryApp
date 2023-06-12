package com.example.mystoryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mystoryapp.data.StoryRepo

class MapsViewModel(private val storyRepos: StoryRepo): ViewModel() {
    fun getStoriesWithLocation() = storyRepos.getStoriesWithLocation()
}
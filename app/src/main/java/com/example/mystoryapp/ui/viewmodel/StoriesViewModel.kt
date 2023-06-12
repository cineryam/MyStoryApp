package com.example.mystoryapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mystoryapp.data.Story
import com.example.mystoryapp.data.StoryRepo

class StoriesViewModel(private val storyRepos: StoryRepo): ViewModel() {
    val getStories: LiveData<PagingData<Story>> = storyRepos.getStories().cachedIn(viewModelScope)
}
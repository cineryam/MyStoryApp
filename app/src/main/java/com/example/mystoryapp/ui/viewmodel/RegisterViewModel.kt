package com.example.mystoryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mystoryapp.data.StoryRepo

class RegisterViewModel(private val storyRepos: StoryRepo) : ViewModel() {
    fun register(name: String, email: String, password: String) = storyRepos.postRegister(name, email, password)
}
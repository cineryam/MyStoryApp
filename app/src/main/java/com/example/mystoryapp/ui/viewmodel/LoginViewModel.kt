package com.example.mystoryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mystoryapp.data.StoryRepo

class LoginViewModel(private val storyRepos: StoryRepo) : ViewModel() {
    fun login(email: String, password: String) = storyRepos.postLogin(email, password)
}
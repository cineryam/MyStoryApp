package com.example.mystoryapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.mystoryapp.data.LoginResponse
import com.example.mystoryapp.data.Outcome
import com.example.mystoryapp.data.StoryRepo
import com.example.mystoryapp.utils.DataDummy
import com.example.mystoryapp.utils.getOrAwaitValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepos: StoryRepo

    private lateinit var loginViewModel: LoginViewModel
    private val dummyLoginResponse = DataDummy.generateDummyLogin()

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(storyRepos)
    }

    @Test
    fun `when login Should Not Null and return success`() {
        val expectedLoginResponse = MutableLiveData<Outcome<LoginResponse>>()
        expectedLoginResponse.value = Outcome.Success(dummyLoginResponse)
        val email = "name@email.com"
        val password = "secretpassword"

        Mockito.`when`(storyRepos.postLogin(email, password)).thenReturn(expectedLoginResponse)

        val actualResponse = loginViewModel.login(email, password).getOrAwaitValue()
        Mockito.verify(storyRepos).postLogin(email, password)
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Outcome.Success)
    }

    @Test
    fun `when Network Error Should Return Error`() {
        val expectedLoginResponse = MutableLiveData<Outcome<LoginResponse>>()
        expectedLoginResponse.value = Outcome.Error("network error")
        val email = "name@email.com"
        val password = "secretpassword"

        Mockito.`when`(storyRepos.postLogin(email, password)).thenReturn(expectedLoginResponse)

        val actualResponse = loginViewModel.login(email, password).getOrAwaitValue()
        Mockito.verify(storyRepos).postLogin(email, password)
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Outcome.Error)
    }
}
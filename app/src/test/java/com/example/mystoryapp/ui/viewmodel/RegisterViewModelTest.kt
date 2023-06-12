package com.example.mystoryapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.mystoryapp.data.Outcome
import com.example.mystoryapp.data.RegisterResponse
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
class RegisterViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepos: StoryRepo

    private lateinit var registerVM: RegisterViewModel
    private val dummyRegisterResponse = DataDummy.generateDummyRegister()

    @Before
    fun setUp() {
        registerVM = RegisterViewModel(storyRepos)
    }

    @Test
    fun `when signUp Should Not Null and return success`() {
        val expectedRegisterResponse = MutableLiveData<Outcome<RegisterResponse>>()
        expectedRegisterResponse.value = Outcome.Success(dummyRegisterResponse)
        val name = "name"
        val email = "name@email.com"
        val password = "secretpassword"

        Mockito.`when`(storyRepos.postRegister(name, email, password)).thenReturn(expectedRegisterResponse)

        val actualResponse = registerVM.register(name, email, password).getOrAwaitValue()
        Mockito.verify(storyRepos).postRegister(name, email, password)
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Outcome.Success)
    }

    @Test
    fun `when Network Error Should Return Error`() {
        val expectedRegisterResponse = MutableLiveData<Outcome<RegisterResponse>>()
        expectedRegisterResponse.value = Outcome.Error("network error")
        val name = "name"
        val email = "name@email.com"
        val password = "secretpassword"

        Mockito.`when`(storyRepos.postRegister(name, email, password)).thenReturn(expectedRegisterResponse)

        val actualResponse = registerVM.register(name, email, password).getOrAwaitValue()
        Mockito.verify(storyRepos).postRegister(name, email, password)
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Outcome.Error)
    }

}
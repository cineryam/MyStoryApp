package com.example.mystoryapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.mystoryapp.data.Outcome
import com.example.mystoryapp.data.PostStoryResponse
import com.example.mystoryapp.data.StoryRepo
import com.example.mystoryapp.utils.DataDummy
import com.example.mystoryapp.utils.getOrAwaitValue
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepos: StoryRepo

    private lateinit var addStoryViewModel: AddStoryViewModel
    private val dummyResponse = DataDummy.generateDummyAddStory()

    @Before
    fun setUp() {
        addStoryViewModel = AddStoryViewModel(storyRepos)
    }

    @Test
    fun `when postStory Should Not Null and return success`() {
        val descriptionText = "Description text"
        val description = descriptionText.toRequestBody("text/plain".toMediaType())

        val file = Mockito.mock(File::class.java)
        val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo name",
            "photo.jpg",
            requestImageFile
        )

        val expectedPostResponse = MutableLiveData<Outcome<PostStoryResponse>>()
        expectedPostResponse.value = Outcome.Success(dummyResponse)

        Mockito.`when`(storyRepos.postStory(imageMultipart, description)).thenReturn(expectedPostResponse)

        val actualResponse = addStoryViewModel.postStory(imageMultipart, description).getOrAwaitValue()
        Mockito.verify(storyRepos).postStory(imageMultipart, description)
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Outcome.Success)
        assertEquals(dummyResponse.error, (actualResponse as Outcome.Success).data.error)
    }

    @Test
    fun `when Network Error Should Return Error`() {
        val descriptionText = "Description text"
        val description = descriptionText.toRequestBody("text/plain".toMediaType())

        val file = Mockito.mock(File::class.java)
        val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo name",
            "photo.jpg",
            requestImageFile
        )

        val expectedPostResponse = MutableLiveData<Outcome<PostStoryResponse>>()
        expectedPostResponse.value = Outcome.Error("network error")

        Mockito.`when`(storyRepos.postStory(imageMultipart, description)).thenReturn(expectedPostResponse)

        val actualResponse = addStoryViewModel.postStory(imageMultipart, description).getOrAwaitValue()
        Mockito.verify(storyRepos).postStory(imageMultipart, description)
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Outcome.Error)
    }
}
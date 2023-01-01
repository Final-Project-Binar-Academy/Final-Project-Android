package com.binar.finalproject14.viewmodel

import com.binar.finalproject14.data.api.request.LoginRequest
import com.binar.finalproject14.data.api.response.auth.AuthResponse
import com.binar.finalproject14.data.api.response.profile.GetUserResponse
import com.binar.finalproject14.data.api.service.UserApi
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response


internal class ProfileViewModelTest {
    private lateinit var service: UserApi


    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun getUserResult():Unit = runBlocking {
        val respProfile = mockk<Call<GetUserResponse>>()
        every {
            runBlocking {
                service.getUserProfile("ashfka")
            }
        } returns respProfile

        val result = service.getUserProfile("ashfka")
        runBlocking {
            service.getUserProfile("ashfka")
        }
        Assert.assertEquals(result, respProfile)
    }
}
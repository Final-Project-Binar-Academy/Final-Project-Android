package com.binar.finalproject14.viewmodel

import com.binar.finalproject14.data.api.request.RegisterRequest
import com.binar.finalproject14.data.api.service.UserApi
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RegisterViewModelTest {

    private lateinit var service: UserApi

    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun getRegisterResult(): Unit = runBlocking {
        val respRegisterAuth = mockk<Response<AuthResponse>>()
        every {
            runBlocking {
                service.registerUser(RegisterRequest("aa", "bb","cc","dd"))
            }
        } returns respRegisterAuth

        val result = service.registerUser(RegisterRequest("aa", "bb","cc","dd"))
        runBlocking {
            service.registerUser(RegisterRequest("aa", "bb","cc","dd"))
        }
        Assert.assertEquals(result, respRegisterAuth)
    }
}
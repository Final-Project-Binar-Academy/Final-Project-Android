package com.binar.finalproject14.viewmodel

import com.binar.finalproject14.data.api.request.LoginRequest
import com.binar.finalproject14.data.api.response.auth.AuthResponse
import com.binar.finalproject14.data.api.service.UserApi
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class LoginViewModelTest {
    private lateinit var service: UserApi

    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun getLoginResult():Unit = runBlocking {
        val respAuth = mockk<Response<AuthResponse>>()
        every {
            runBlocking {
                service.loginUser(LoginRequest("aa", "bb"))
            }
        }returns respAuth

        val result = service.loginUser(LoginRequest("aa", "bb"))
        runBlocking {
            service.loginUser(LoginRequest("aa", "bb"))
        }
        assertEquals(result, respAuth)
    }
}
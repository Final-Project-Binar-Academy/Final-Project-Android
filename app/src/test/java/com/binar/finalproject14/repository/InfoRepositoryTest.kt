package com.binar.finalproject14.repository

import com.binar.finalproject14.data.api.response.InfoResponse
import com.binar.finalproject14.data.api.service.ApiHelperImpl
import com.binar.finalproject14.data.api.service.NewsApi
import com.binar.finalproject14.data.api.service.UserApi
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import retrofit2.Call

class InfoRepositoryTest {

    private lateinit var service: NewsApi
    private lateinit var serviceUser: UserApi
    private lateinit var apiHelper: ApiHelperImpl
    private lateinit var repo: InfoRepository
    @Before
    fun setUp(){
        service = mockk()
        serviceUser = mockk()
        apiHelper = ApiHelperImpl(serviceUser, service)
        repo = InfoRepository(apiHelper)
    }

    @Test
    fun getInfo(): Unit = runBlocking {
        val respAllInfo = mockk<Call<InfoResponse>>()

        every {
            runBlocking {
                service.getInfo()
            }
        } returns respAllInfo

        repo.getInfo()

        verify {
            runBlocking { service.getInfo() }
        }
    }
}
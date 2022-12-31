package com.binar.finalproject14.repository

import com.binar.finalproject14.data.api.response.InfoResponse
import com.binar.finalproject14.data.api.response.airport.AirportResponse
import com.binar.finalproject14.data.api.response.ticket.FlightIdResponse
import com.binar.finalproject14.data.api.response.ticket.FlightResponse
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

class MainRepositoryTest {

    private lateinit var service: NewsApi
    private lateinit var serviceUser: UserApi
    private lateinit var apiHelper: ApiHelperImpl
    private lateinit var repo: MainRepository

    @Before
    fun setUp() {
        service = mockk()
        serviceUser = mockk()
        apiHelper = ApiHelperImpl(serviceUser, service)
        repo = MainRepository(apiHelper)
    }


    @Test
    fun getFlight() : Unit = runBlocking {
        val respAllInfo = mockk<Call<FlightResponse>>()

        every {
            runBlocking {
                serviceUser.getFlight()
            }
        } returns respAllInfo

        repo.getFlight()

        verify {
            runBlocking { serviceUser.getFlight() }
        }
    }

    @Test
    fun getAirport() : Unit = runBlocking {
        val respAllInfo = mockk<Call<AirportResponse>>()

        every {
            runBlocking {
                serviceUser.getAirport()
            }
        } returns respAllInfo

        repo.getAirport()

        verify {
            runBlocking { serviceUser.getAirport() }
        }
    }
}
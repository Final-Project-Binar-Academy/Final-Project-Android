package com.binar.finalproject14.viewmodel

import com.binar.finalproject14.data.api.request.PaymentRequest
import com.binar.finalproject14.data.api.response.payment.PaymentResponse
import com.binar.finalproject14.data.api.service.UserApi
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response


internal class PaymentViewModelTest {
    private lateinit var service: UserApi

    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun updatePaymentResult():Unit = runBlocking {
        val respPayment = mockk<Call<PaymentResponse>>()
        every {
            runBlocking {
                service.updatePayment("abfdkjf", PaymentRequest(1, 2))
            }
        } returns respPayment

        val result = service.updatePayment("abfdkjf",PaymentRequest(1, 2))
        runBlocking {
            service.updatePayment("abfdkjf",PaymentRequest(1, 2))
        }
        Assert.assertEquals(result, respPayment)
    }
}
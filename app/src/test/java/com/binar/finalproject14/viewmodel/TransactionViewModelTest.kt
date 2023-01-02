package com.binar.finalproject14.viewmodel

import com.binar.finalproject14.data.api.request.AddTransaction
import com.binar.finalproject14.data.api.response.auth.AuthResponse
import com.binar.finalproject14.data.api.response.transaction.CancelResponse
import com.binar.finalproject14.data.api.response.transaction.add.TransactionResponse
import com.binar.finalproject14.data.api.service.UserApi
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Call

internal class TransactionViewModelTest {
    private lateinit var service: UserApi
    private lateinit var viewModel : TransactionViewModel

    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun getTransactionIdResult():Unit = runBlocking {
        val resp = mockk<Call<TransactionResponse>>()
        every {
            runBlocking {
                service.getTransactionId("aa", 1)
            }
        }returns resp

        val result = service.getTransactionId("aa", 1)
        runBlocking {
            service.getTransactionId("aa", 1)
        }
        assertEquals(result, resp)
    }
    @Test
    fun addTransactionResult():Unit = runBlocking {
        val resp = mockk<Call<TransactionResponse>>()
        every {
            runBlocking {
                service.addTransaction("aa", AddTransaction("asf", "asf","asfgaj","qyh",1,5,2))
            }
        }returns resp

        val result = service.addTransaction("aa", AddTransaction("asf", "asf","asfgaj","qyh",1,5,2))
        runBlocking {
            service.addTransaction("aa", AddTransaction("asf", "asf","asfgaj","qyh",1,5,2))
        }
        assertEquals(result, resp)
    }
    @Test
    fun cancelTransactionResult():Unit = runBlocking {
        val resp = mockk<Call<CancelResponse>>()
        every {
            runBlocking {
                service.cancelTransaction("aa", 1)
            }
        }returns resp

        val result = service.cancelTransaction("aa", 1)
        runBlocking {
            service.cancelTransaction("aa", 1)
        }
        assertEquals(result, resp)
    }

}
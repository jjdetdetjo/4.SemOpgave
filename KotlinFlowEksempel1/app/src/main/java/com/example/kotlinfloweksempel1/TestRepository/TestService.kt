package com.example.kotlinfloweksempel1.TestRepository

import com.example.kotlinfloweksempel1.TestModel.TestModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.*

interface TestService{
    @GET("messages")
    fun getAllMessages(): Call<List<TestModel>>
}
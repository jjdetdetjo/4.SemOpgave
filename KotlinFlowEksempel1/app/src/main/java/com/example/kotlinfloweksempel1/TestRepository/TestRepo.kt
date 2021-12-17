package com.example.kotlinfloweksempel1.TestRepository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinfloweksempel1.TestModel.TestModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class TestRepo {
    private val url ="https://anbo-restmessages.azurewebsites.net/api/"

    private val testService: TestService
    private val refreshInterval: Long = 5000
    var testMessages: List<TestModel>? = null

    private val mockService: MockAPI = MockAPI()
    //var message: TestModel? = null
    //emit(message)
    //message = testMessages?.first()

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create()).build()
        testService = build.create(TestService::class.java)
    }

    val returnMockMessageWithFlowTest: Flow<TestModel> = flow {
        Log.i("flowvalues", "Flow started")
        while (true){
            var message = mockService.getMockMessage()
            Log.i("flowvalues", "emitting with id ${message.id}")
            emit(message)
            Log.i("flowvalues", "This wont be logged after 3rd emit")
            delay(refreshInterval)
        }
    }

    val flow: Flow<TestModel?> = flow {
        Log.i("flowvalues", "Flow started")
        while (true){
            getData()
            Log.i("flowvalues", "Starting to emit")
            if (!testMessages.isNullOrEmpty()){
                for (message in testMessages!!){
                    Log.i("flowvalues", "$message")
                    emit(message)
                }
            }

            delay(refreshInterval)
        }
    }

    fun getData() {
        Log.i("flowvalues", "Getting data")
        testService.getAllMessages().enqueue(object: Callback<List<TestModel>>{
            override fun onResponse(call: Call<List<TestModel>>, response: Response<List<TestModel>>) {
                if (response.isSuccessful){
                    testMessages = response.body()
                }
                else{
                    val message = response.code().toString() + " " + response.message()
                    Log.i("flowvalues", message)
                }
            }

            override fun onFailure(call: Call<List<TestModel>>, t: Throwable) {
                Log.i("flowvalues", t.message.toString())
            }
        })
    }
}
package com.example.kotlinfloweksempel1.TestViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinfloweksempel1.TestModel.TestModel
import com.example.kotlinfloweksempel1.TestRepository.TestRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Call

class TestViewModel : ViewModel() {
    private val repo = TestRepo()
    var testModelList: MutableLiveData<TestModel> = MutableLiveData<TestModel>()

    suspend fun collectFlow(){
        repo.flow.collect{ model ->
            testModelList.postValue(model)
        }
    }

    suspend fun collectMockMessageTest(){
        Log.i("flowvalues", "Calling flow")
        repo.returnMockMessageWithFlowTest
            .take(3)
            .collect {
                    model -> testModelList.postValue(model)
            }
    }

    var viewFlow: Flow<TestModel> = flow{
        repo.returnMockMessageWithFlowTest
            .take(3)
            .collect{
                    model -> emit(model)
            }
    }

    suspend fun collectFlowAlternative(){
        repo.testMessages?.asFlow()?.collect{
                model -> testModelList.postValue(model)
        }
    }
}

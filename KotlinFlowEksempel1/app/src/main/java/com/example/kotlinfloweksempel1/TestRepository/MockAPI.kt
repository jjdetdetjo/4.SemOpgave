package com.example.kotlinfloweksempel1.TestRepository

import com.example.kotlinfloweksempel1.TestModel.TestModel

class MockAPI {

    var id = 1

    fun getMockMessage(): TestModel {
        var testModel = TestModel(id, "Content of message $id", "User", 0)
        id++
        return testModel
    }
}
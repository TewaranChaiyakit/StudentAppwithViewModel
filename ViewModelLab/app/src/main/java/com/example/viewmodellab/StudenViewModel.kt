package com.example.viewmodellab

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class StudentViewModel : ViewModel(){
    var data = mutableStateListOf<StudentModel>()

    fun addStudent(name:String, studentId:String){
        //count data
        var count:Int = data.count()
        data.add(
            StudentModel(count++,name,studentId)
        )
    }
}
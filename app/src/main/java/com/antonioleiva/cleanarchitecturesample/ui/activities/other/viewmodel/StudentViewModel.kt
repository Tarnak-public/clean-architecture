package com.antonioleiva.cleanarchitecturesample.ui.activities.other.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.antonioleiva.cleanarchitecturesample.ui.factory.BaseViewModel
import com.antonioleiva.data.db.entity.Student
import com.antonioleiva.data.repository.RoomDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StudentViewModel @Inject constructor(
    private val roomDBRepository: RoomDBRepository

) : BaseViewModel(), LifecycleObserver {
    private val tagDebug: String = "fetchData()"
    private val insertedId = MutableLiveData<Long>()
    private val error = MutableLiveData<String>()
    private var _userFinalList = MutableLiveData<List<Student>>()

    init {
        viewModelScope.launch {
            fetchStudentData()
        }
//        val result = MediatorLiveData<Unit>()
//        result.addSource(insertedId) {
//            Log.d(tagDebug, "StudentViewModel preLaunch MediatorLiveData()")
//        }
    }

    private fun fetchStudentData() {
        viewModelScope.launch {
            val students = roomDBRepository.fetchStudents()
            Log.d(
                tagDebug,
                "StudentViewModel fetchStudentData() ${students.size} " + Thread.currentThread().name
            )
            _userFinalList.postValue(students)
        }
    }

    fun insertStudentInfo(student: Student) {
        viewModelScope.launch {
            if (student.fName.isNotEmpty() && student.lName.isNotEmpty() && student.standard.isNotEmpty() && student.age.toString()
                    .isNotEmpty()
            ) {
                val userId: Long = roomDBRepository.insertStudentData(student)
                insertedId.postValue(userId)
                Log.d(tagDebug, "StudentViewModel insertStudentInfo() userId = $userId")
                fetchStudentData()
            } else {
                error.postValue("Input Fields cannot be Empty")
            }
        }
    }

    fun fetchError(): LiveData<String> = error

    fun fetchInsertedId(): LiveData<Long> = insertedId

    fun fetchDatabaseChanges(): LiveData<List<Student>> = _userFinalList
}
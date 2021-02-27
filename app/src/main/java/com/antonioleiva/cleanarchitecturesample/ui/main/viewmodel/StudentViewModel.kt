package com.antonioleiva.cleanarchitecturesample.ui.main.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.antonioleiva.cleanarchitecturesample.ui.base.BaseViewModel
import com.antonioleiva.data.db.entity.Student
import com.antonioleiva.data.repository.RoomDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentViewModel @Inject constructor(
    private val roomDBRepository: RoomDBRepository

) : BaseViewModel(), LifecycleObserver {

    private val insertedId = MutableLiveData<Long>()
    private val error = MutableLiveData<String>()

//    var userFinalList: LiveData<MutableList<Student>> = MutableLiveData()
//    private var userFinalList: MutableLiveData<MutableList<Student>> = MutableLiveData()

//    val _userFinalList: MutableLiveData<List<Student>> = MutableLiveData()
//    val userFinalList: LiveData<List<Student>> = _userFinalList

//    private var _userFinalList = MutableLiveData<List<Student>>()
//    var userFinalList: LiveData<List<Student>>
//        get() = _userFinalList
//        set(value) {
//            _userFinalList = value as MutableLiveData<List<Student>>
//        }

    var studentsList: List<Student> = emptyList()

    private var _userFinalList = MutableLiveData<List<Student>>()
    var userFinalList: LiveData<List<Student>>
        get() = _userFinalList
        set(value) {
            _userFinalList = value as MutableLiveData<List<Student>>
        }

    init {
        viewModelScope.launch {
            fetchStudentData()
        }
    }

//
//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    fun refreshStudentData() {
//        GlobalScope.launch {
//            println("fetchData() StudentViewModel refreshStudentData()")
//            fetchStudentData()
//        }
//    }

    private suspend fun fetchStudentData() {
        viewModelScope.launch {
            val students = roomDBRepository.fetchStudents()
            println("fetchData() StudentViewModel fetchStudentData() $students")
            studentsList = students
            _userFinalList.postValue(students)

            println("fetchData() StudentViewModel fetchStudentData() _userFinalList ${_userFinalList.value}")
            println("fetchData() StudentViewModel fetchStudentData() studentsList $studentsList")
        }
    }

    fun insertStudentInfo(student: Student) {
        viewModelScope.launch {
            if (student.fName.isEmpty() ||
                student.lName.isEmpty() ||
                student.standard.isEmpty() ||
                student.age.toString().isEmpty()
            ) {
                error.postValue("Input Fields cannot be Empty")
            } else {
                val userId: Long = roomDBRepository.insertStudentData(student)
                insertedId.postValue(userId)
//                roomDBRepository.fetchStudentsSync()
//                println("fetchData() StudentViewModel insertStudentInfo() now call for fetchStudentData()")
//                fetchStudentData()
            }
        }
    }

    fun fetchError(): LiveData<String> = error

    fun fetchInsertedId(): LiveData<Long> = insertedId
}
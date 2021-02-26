package com.antonioleiva.cleanarchitecturesample.ui.main.viewmodel

import androidx.lifecycle.*
import com.antonioleiva.cleanarchitecturesample.ui.base.BaseViewModel
import com.antonioleiva.data.db.entity.Student
import com.antonioleiva.data.repository.RoomDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StudentViewModel @Inject constructor(
    private val roomDBRepository: RoomDBRepository
) : BaseViewModel(), LifecycleObserver {

    private val insertedId = MutableLiveData<Long>()
    private val error = MutableLiveData<String>()

//    var userFinalList: LiveData<MutableList<Student>> = MutableLiveData()
//    private var userFinalList: MutableLiveData<MutableList<Student>> = MutableLiveData()

    private val _userFinalList: MutableLiveData<MutableList<Student>> = MutableLiveData()
    val userFinalList: LiveData<MutableList<Student>> = _userFinalList
//    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
//    suspend fun fetchStudentData() {
//        withContext(Dispatchers.IO) {
//            viewModelScope.launch {
//                _userFinalList.postValue(roomDBRepository.fetchStudents())
//            }
//        }
//    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    suspend fun fetchStudentData() {
            viewModelScope.launch {
                _userFinalList.postValue(roomDBRepository.fetchStudents())
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
            }
        }
    }

    fun fetchError(): LiveData<String> = error

    fun fetchInsertedId(): LiveData<Long> = insertedId
}
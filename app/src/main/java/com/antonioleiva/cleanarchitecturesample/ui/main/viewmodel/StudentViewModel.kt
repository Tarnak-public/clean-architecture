package com.mindorks.framework.mvvm.presentation.ui.main.viewmodel

import androidx.lifecycle.*
import com.antonioleiva.data.db.entity.Student
import com.mindorks.framework.mvvm.data.repository.RoomDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.supercharge.fragmentfactoryandhilt.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentViewModel @Inject constructor(
    private val roomDBRepository: RoomDBRepository
) : BaseViewModel(), LifecycleObserver {

    private val insertedId = MutableLiveData<Long>()
    private val error = MutableLiveData<String>()
    var userFinalList: LiveData<MutableList<Student>> = MutableLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchStudentData() {
        viewModelScope.launch {
            userFinalList = roomDBRepository.fetchStudents()
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
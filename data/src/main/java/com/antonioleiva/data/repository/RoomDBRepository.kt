package com.antonioleiva.data.repository

import com.antonioleiva.data.db.StudentDao
import com.antonioleiva.data.db.entity.Student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomDBRepository @Inject constructor(private val studentDao: StudentDao) {
    suspend fun insertStudentData(student: Student) =
        withContext(Dispatchers.IO) {
            println("fetchData() insertStudentData()")
            studentDao.insert(student)
        }

//    suspend fun fetchStudents() = withContext(Dispatchers.IO) {
//        println("fetchData() fetchStudents() in RoomDBRepository() " + studentDao.fetch())
//        studentDao.fetch()
//    }

    suspend fun fetchStudents() = CoroutineScope(Dispatchers.IO).async {
        println("fetchData() fetchStudents() in RoomDBRepository() " + studentDao.fetch())
        return@async studentDao.fetch()
    }.await()

}
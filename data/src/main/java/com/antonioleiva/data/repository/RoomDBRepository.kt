package com.antonioleiva.data.repository

import com.antonioleiva.data.db.StudentDao
import com.antonioleiva.data.db.entity.Student
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomDBRepository @Inject constructor(private val studentDao: StudentDao) {
    suspend fun insertStudentData(student: Student) =
        withContext(Dispatchers.IO) { studentDao.insert(student) }

    suspend fun fetchStudents() = withContext(Dispatchers.IO) { studentDao.fetch() }
}
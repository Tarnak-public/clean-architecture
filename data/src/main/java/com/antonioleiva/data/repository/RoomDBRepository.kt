package com.antonioleiva.data.repository

import com.antonioleiva.data.db.entity.Student
import com.antonioleiva.data.db.StudentDao
import javax.inject.Inject

class RoomDBRepository @Inject constructor(private val studentDao: StudentDao) {
    suspend fun insertStudentData(student: Student) = studentDao.insert(student)

    suspend fun fetchStudents() = studentDao.fetch()
}
package com.antonioleiva.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.antonioleiva.data.db.entity.Student

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(student: Student): Long

    @Query("select * From student ORDER BY studentId ASC")
    fun  fetch() : MutableList<Student>
//    fun  fetch() : LiveData<MutableList<Student>>
}
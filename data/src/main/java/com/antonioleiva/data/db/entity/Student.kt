package com.antonioleiva.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
data class Student (
    @PrimaryKey(autoGenerate = true)
    val studentId : Long = 0L,
    val fName : String,
    val lName : String,
    val standard : String,
    val age : Int

)
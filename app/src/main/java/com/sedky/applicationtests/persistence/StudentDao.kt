package com.sedky.applicationtests.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sedky.applicationtests.data.model.Students
import org.jetbrains.annotations.NotNull

@Dao
interface StudentDao {

    @Insert
    fun insert(student: Students)

    @Update
    fun update(student: Students)

    @Delete
    fun deleteItem(student: Students): Int

    @Query("DELETE  From student_table")
    fun deleteAllStudents()

    @Query("SELECT * FROM STUDENT_TABLE")
    fun getAllStudents(): List<Students>
}
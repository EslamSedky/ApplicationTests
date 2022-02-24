package com.sedky.applicationtests.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_table")
data class Students(

    var firstName: String,
    var lastName: String,
    var degree: Double,
    var isAdult: Boolean
) {
    @PrimaryKey(autoGenerate = true) var id :Int? = null
}
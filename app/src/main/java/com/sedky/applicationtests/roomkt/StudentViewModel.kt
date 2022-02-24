package com.sedky.applicationtests.roomkt

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sedky.applicationtests.data.model.Students
import com.sedky.applicationtests.persistence.StudentDao
import kotlinx.coroutines.*

class StudentViewModel(
    val databaseDAO: StudentDao,
    application: Application
) : AndroidViewModel(application) {

    private val TAG: String? = "StudentViewModel"
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var studentLifeData = MutableLiveData<List<Students>>()
//    private val student = databaseDAO.getAllStudents()

    init {
//        Log.i(TAG, "BeforeGetStudentFromDB: $student")
        initializeStudents()
//        Log.i(TAG, "afterGetStudentFromDB: $student")
        Log.i(TAG, "getDataFomDB: ${studentLifeData.value}")
    }

    private fun initializeStudents() {
        uiScope.launch {
            getAllStudents()
        }
    }

    suspend fun getAllStudents(): LiveData<List<Students>> {

            studentLifeData.value = getStudentsFomDatabase()
            Log.i(TAG, "getAllStudentsWithContext: ${studentLifeData.value?.get(0)}")

        Log.i(TAG, "getAllStudents: ${studentLifeData.value?.get(0)}")
        return studentLifeData
    }

    private suspend fun getStudentsFomDatabase(): List<Students>? {
        return withContext(Dispatchers.IO) {
            val allStudents = databaseDAO.getAllStudents()
            allStudents
        }
    }

    fun onStartInsert(students: Students) {
        uiScope.launch {
            insert(students)
        }
    }

    private suspend fun insert(students: Students) {
        withContext(Dispatchers.IO) {
            databaseDAO.insert(students)
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
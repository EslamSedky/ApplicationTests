package com.sedky.applicationtests.roomkt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sedky.applicationtests.R

class TestRoom : AppCompatActivity() {


    private val TAG: String? = "TestRoomActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_room)

//        var viewModel = ViewModelProvider(this,StudentViewModelFactory(dataSource,application))
//            .get(StudentViewModel::class.java)
//        binding.viewModel = viewModel
//
//        CoroutineScope(Dispatchers.Main).launch {
//            Log.i(TAG, "getDataFomDB: ${viewModel.getAllStudents().value}")
//        }


    }
}
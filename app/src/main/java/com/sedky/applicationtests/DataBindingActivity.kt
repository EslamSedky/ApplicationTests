package com.sedky.applicationtests

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sedky.applicationtests.data.model.Students
import com.sedky.applicationtests.databinding.ActivityDataBindingBinding

class DataBindingActivity : AppCompatActivity() {
    private lateinit var editTextLastName: EditText
    private lateinit var editTextFirstName: EditText
    private lateinit var editTextDegree: EditText
    lateinit var binding: ActivityDataBindingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding)

        binding.apply {
            editTextFirstName = editTextTextFirstName
            editTextLastName = editTextTextLastName
            editTextDegree = editTextTextDegree
        }
        binding.textView2

//        binding.button4.setOnClickListener {
//            val students = Students(
//                editTextFirstName.text.toString(),
//                editTextLastName.text.toString(),
//                editTextDegree.text.toString().toDouble()
//
//            )
//            binding.myFirstName = students
//            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.hideSoftInputFromWindow(it.windowToken, 0)
//        }


    }
}
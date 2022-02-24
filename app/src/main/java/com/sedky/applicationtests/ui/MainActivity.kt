package com.sedky.applicationtests.ui

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sedky.applicationtests.Commerce
import com.sedky.applicationtests.DataBindingActivity
import com.sedky.applicationtests.MyReceiver
import com.sedky.applicationtests.R
import com.sedky.applicationtests.data.api.ApiClient
import com.sedky.applicationtests.data.api.ApiHelper
import com.sedky.applicationtests.data.model.Students
import com.sedky.applicationtests.databinding.ActivityMainBinding
import com.sedky.applicationtests.roomkt.TestRoom
import com.sedky.applicationtests.service.MyService
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var intentService: Intent
    private lateinit var statusText: TextView
    private lateinit var countText: TextView
    private var count: Int = 1
    private lateinit var seekBar: SeekBar
    private val TAG: String = "testStations"
    private lateinit var viewModel: MainViewModel
    private lateinit var receiver: MyReceiver
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var bindingView: ActivityMainBinding

    suspend fun performTask(taskNumber: Int): Deferred<String> =
        coroutineScope.async(Dispatchers.Main) {
            delay(50000)
            return@async "Finished coroutines number ${taskNumber}"
        }

    fun launchCoroutines(v: View) {
        (1..count).forEach {
            statusText.text = "Started coroutine ${it}"
            coroutineScope.launch(Dispatchers.Main) {
                statusText.text = performTask(it).await()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingView = ActivityMainBinding.inflate(layoutInflater)
        val root = bindingView.root
        setContentView(root)
        val quantityString = resources.getQuantityString(R.plurals.tutorials, 5)
        Log.i(TAG, quantityString)


        //receiver
        receiver = MyReceiver()
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(receiver, it)

        }

        setUpViewModel()
        setUpObservers()

//        binding.apply {
//            seekBar
//            countText
//            statusText
//        }
        seekBar = findViewById(R.id.seekBar)
        countText = findViewById(R.id.countText)
        statusText = findViewById(R.id.textView)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                count = progress
                countText.text = "${count} Coroutines"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        //service
        intentService = Intent(this, MyService::class.java)

        //dataBinding

        bindingView.goToBindingTest.setOnClickListener {
            startActivity(Intent(this,DataBindingActivity::class.java))
        }

        //navigationTest
        bindingView.goToNavigationTest.setOnClickListener {
            startActivity(Intent(this, Commerce::class.java))
        }
        bindingView.goToRoomTest.setOnClickListener {
            startActivity(Intent(this,TestRoom::class.java))
        }

    }

    private fun setUpObservers() {

        viewModel.getStation().observe(this, Observer {
            Log.i(TAG, "setUpObservers: " + it.status)
            Log.i(TAG, "setUpObservers: ${it.data}")
            it.let {
                it.data?.let { it.data[0].first_name }
            }
        })
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory(ApiHelper(ApiClient.getRetrofit())))
            .get(MainViewModel::class.java)

    }




    fun startMyService(view: View) {

        startService(intentService)
        Log.i(TAG, "startMyService: ")
    }

    fun stopMyService(view: View) {
        stopService(intentService)
    }

}
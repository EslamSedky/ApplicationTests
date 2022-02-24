package com.sedky.applicationtests.roomkt.sleepdetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sedky.applicationtests.databinding.FragmentSleepDetailsBinding
import com.sedky.applicationtests.persistence.StudentDB


class SleepDetailsFragment : Fragment() {


    private val TAG: String = "SleepDetailsFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val dataBinding = FragmentSleepDetailsBinding.inflate(
            inflater,
            container,
            false
        )
        val args = SleepDetailsFragmentArgs.fromBundle(requireArguments())
        Log.i(TAG, "onCreateView: $args")
        val application = requireNotNull(this.activity).application
        val dataSource = StudentDB.getInstance(application).sleepDataDao
        val viewModelFactory = SleepDetailsViewModelFactory(args.sleepNightKey, dataSource)
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(SleepDetailsViewModel::class.java)
        dataBinding.sleepDetailsViewModel = viewModel
        dataBinding.lifecycleOwner = this

        viewModel.navigateToSleepTracker.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    SleepDetailsFragmentDirections
                        .actionSleepDetailsFragmentToSleepTrackerFragment()
                )
                viewModel.doneNavigating()
            }
        })
        return dataBinding.root
    }
}
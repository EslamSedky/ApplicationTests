package com.sedky.applicationtests.roomkt.sleeptracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sedky.applicationtests.R
import com.sedky.applicationtests.databinding.FragmentSleepTrackerBinding
import com.sedky.applicationtests.persistence.StudentDB

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SleepTrackerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SleepTrackerFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_sleep_tracker, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = StudentDB.getInstance(application).sleepDataDao

        val viewModel =
            ViewModelProvider(this, SleepTrackerViewModelFactory(dataSource, application))
                .get(SleepTrackerViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        val gridLayout = GridLayoutManager(activity, 3)
        gridLayout.spanSizeLookup = object:GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int) = when(position){
                0 -> 2
                else -> 1
            }

        }
        binding.recycler.layoutManager = gridLayout


        viewModel.navigateToSleepNightQuality.observe(viewLifecycleOwner, Observer { night ->
            night?.let {
                this.findNavController().navigate(
                    SleepTrackerFragmentDirections
                        .actionSleepTrackerFragmentToSleepQualityFragment2(night.nightId)
                )
                viewModel.doneNavigating()
            }
        })

        viewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    "All your data is gone forever",
                    Snackbar.LENGTH_LONG
                ).show()
                viewModel.doneShowingSnackBar()
            }
        })

        val adapter = SleepTrackerAdapter(SleepTrackerAdapter.SleepNightListener { nightId ->
            viewModel.onSleepNightClicked(nightId)
        })

        binding.recycler.adapter = adapter

        viewModel.navigateToSleepDataQuality.observe(viewLifecycleOwner, Observer { night ->

            night?.let {
                this.findNavController().navigate(
                    SleepTrackerFragmentDirections
                        .actionSleepTrackerFragmentToSleepDetailsFragment(night)
                )
                viewModel.onSleepDataQualityNavigated()

            }
        })
        viewModel.nights.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToSleepDetails.observe(viewLifecycleOwner, Observer { night ->
            night?.let {
                this.findNavController().navigate(
                SleepTrackerFragmentDirections
                    .actionSleepTrackerFragmentToSleepDetailsFragment(night))
            }
            viewModel.onSleepDetailsNavigated()
        })
        return binding.root
    }
}
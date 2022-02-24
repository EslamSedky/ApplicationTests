package com.sedky.applicationtests.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sedky.applicationtests.R
import com.sedky.applicationtests.data.model.Students
import com.sedky.applicationtests.databinding.FragmentHomeBinding
import com.sedky.applicationtests.helper.MyHandler
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val TAG: String?= "HomeFragment"
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
//        val homeViewModel : HomeViewModel by viewModels()
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val students = Students("saif", "sedky", 100.0,true)
        binding.student = students
        binding.handlers = MyHandler()

        val vi : HomeViewModel by viewModels()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
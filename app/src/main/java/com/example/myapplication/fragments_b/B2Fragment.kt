package com.example.myapplication.fragments_b

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentB2Binding

class B2Fragment : Fragment(R.layout.fragment_b2) {
    private var _binding: FragmentB2Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentB2Binding.bind(view)
        binding.textView2.text = arguments?.getString("Key").orEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
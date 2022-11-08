package com.example.myapplication.fragments_a

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentA2Binding

class A2Fragment : Fragment(R.layout.fragment_a2) {
    private var _binding: FragmentA2Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentA2Binding.bind(view)
        binding.button.setOnClickListener{
            findNavController().navigate(R.id.action_a2Fragment_to_a3Fragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
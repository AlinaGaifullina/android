package com.example.myapplication.fragments_a

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentABinding

class AFragment : Fragment(R.layout.fragment_a) {
    private var _binding: FragmentABinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentABinding.bind(view)
        binding.button.setOnClickListener{
            findNavController().navigate(R.id.action_AFragment_to_a2Fragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
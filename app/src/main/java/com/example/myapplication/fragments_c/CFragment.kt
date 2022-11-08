package com.example.myapplication.fragments_c

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCBinding

class CFragment : Fragment(R.layout.fragment_c) {
    private var _binding: FragmentCBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCBinding.bind(view)

        (requireActivity() as? MainActivity)?.bottomNavigationVisibility(true)

        binding.button.setOnClickListener{
            findNavController().navigate(R.id.action_CFragment_to_c2Fragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
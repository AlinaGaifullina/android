package com.example.myapplication.fragments_c

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentC2Binding

class C2Fragment : Fragment(R.layout.fragment_c2) {
    private var _binding: FragmentC2Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentC2Binding.bind(view)

        (requireActivity() as? MainActivity)?.bottomNavigationVisibility(false)

        binding.button.setOnClickListener{
            findNavController().navigate(R.id.action_c2Fragment_to_c3Fragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
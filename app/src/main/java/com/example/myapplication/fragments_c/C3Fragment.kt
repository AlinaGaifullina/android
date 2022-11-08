package com.example.myapplication.fragments_c

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentC3Binding

class C3Fragment : Fragment(R.layout.fragment_c3) {
    private var _binding: FragmentC3Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentC3Binding.bind(view)

        (requireActivity() as? MainActivity)?.bottomNavigationVisibility(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
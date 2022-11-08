package com.example.myapplication.fragments_b

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentBBinding

class BFragment : Fragment(R.layout.fragment_b) {
    private var _binding: FragmentBBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBBinding.bind(view)

        binding.button.setOnClickListener{
            val bundle = Bundle()
            val text = binding.et1.editText?.text.toString()
            bundle.putString("Key", text)
            findNavController().navigate(R.id.action_BFragment_to_b2Fragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
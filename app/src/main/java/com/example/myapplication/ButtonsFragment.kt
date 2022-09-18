package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentButtonsBinding

class ButtonsFragment : Fragment(R.layout.fragment_buttons) {
    private var _binding: FragmentButtonsBinding? = null
    private val binding get() = _binding!!

    var counterClicks : Int = 0
    var counterColors : Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentButtonsBinding.bind(view)

        binding.button.setOnClickListener {
            counterClicks++
        }

        binding.button3.setOnClickListener {
            counterColors++
            if(counterColors > 2) {
                counterColors = 0
            }
        }

        binding.button2.setOnClickListener {
            val fragment = DetailFragment.newInstance(
                counterValue = counterClicks,
                color = counterColors
            )
            parentFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_fragments_container, fragment)
                .commit()
        }

    }
    companion object{
        fun newInstance(counterValue: Int, color: Int) : ButtonsFragment {
            val args = Bundle().apply {
                putInt("key1", counterValue)
                putInt("key2", color)
            }
            val fragment = ButtonsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
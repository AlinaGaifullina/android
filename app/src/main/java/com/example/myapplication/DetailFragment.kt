package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentDetailBinding


class DetailFragment : Fragment(R.layout.fragment_detail) {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        val color: Int = arguments?.getInt("key2") ?: 0
        var colorID = 0

        if(color == 0){
            colorID = R.color.red
        }
        if(color == 1){
            colorID = R.color.blue
        }
        if(color == 2){
            colorID = R.color.green
        }

        with(binding){
            textView.text = (arguments?.getInt("key1") ?: 0).toString()
            imageView.setColorFilter(resources.getColor(colorID))
            if(textView.text == "0"){
                textView.visibility = View.INVISIBLE
            }
        }
    }

    companion object{
        fun newInstance(counterValue: Int, color: Int) : DetailFragment {
            val args = Bundle().apply {
                putInt("key1", counterValue)
                putInt("key2", color)
            }
            val fragment = DetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
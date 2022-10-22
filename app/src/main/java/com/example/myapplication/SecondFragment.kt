package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding by lazy { _binding!!}

    private var dogID: Int  = -100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dogID = arguments?.getInt(DOG_ID_TAG)?:-100

        _binding = FragmentSecondBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout() {
        val dog = DogRepository.dogs[dogID]

        with(binding) {
            tvDescription.setText(dog.description)
            tvTitle.setText(dog.name)

            Glide.with(root)
                .load(dog.imageUrl)
                .into(ivDog)
        }
    }

    companion object {
        const val SECOND_FRAGMENT_TAG = "SECOND_FRAGMENT_TAG"
        const val DOG_ID_TAG = "DOG_ID_TAG"
        fun newInstance(itemPosition : Int) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putInt(DOG_ID_TAG, itemPosition)
                }
            }
    }
}
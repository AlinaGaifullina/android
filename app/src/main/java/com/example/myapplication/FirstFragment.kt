package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding by lazy { _binding!! }

    private val containerID = R.id.container

    private lateinit var adapter: DogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentFirstBinding.inflate(layoutInflater)
        initRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private fun initRecyclerView() {
        with(binding) {
            adapter = DogAdapter(
                dogs = DogRepository.dogs,
                glide = Glide.with(binding.root.context),
                onItemClick = ::onDogItemWasClicked
            )
            rvDog.adapter = adapter
        }
    }


    private fun onDogItemWasClicked(itemPosition: Int) {

        DogRepository.dogs[itemPosition].colorID = R.color.gray

        adapter.notifyItemChanged(itemPosition)

        parentFragmentManager.beginTransaction()
            .replace(
                containerID,
                SecondFragment.newInstance(itemPosition = itemPosition),
                SecondFragment.SECOND_FRAGMENT_TAG
            )
            .addToBackStack(null)
            .commit()
    }

    companion object {
        const val FIRST_FRAGMENT_TAG = "FIRST_FRAGMENT_TAG"
        fun newInstance(bundle: Bundle) =
            FirstFragment().apply {
                arguments = Bundle().apply {
                    putAll(bundle)
                }
            }
    }
}
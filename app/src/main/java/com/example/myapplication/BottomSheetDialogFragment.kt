package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentBottomSheetDialogBinding

class BottomSheetDialogFragment : Fragment() {
    private var _binding: FragmentBottomSheetDialogBinding? = null
    private val binding by lazy { _binding!! }

    private lateinit var adapter: AppAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentBottomSheetDialogBinding.inflate(layoutInflater)

        initRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as? MainActivity)?.bottomNavigationVisibility(true)
        return binding.root
    }

    private fun initRecyclerView() {
        with(binding) {
            adapter = AppAdapter(
                items = ItemRepository.items,
                onItemClick = ::onItemWasClicked
            )
            rvItem.adapter = adapter
        }
    }

    private fun onItemWasClicked(itemPosition: Int) {

        adapter.notifyItemChanged(itemPosition)

        when(itemPosition) {
            0 -> findNavController().navigate(R.id.action_bottomSheetDialogFragment_to_a3Fragment)

            1 -> findNavController().navigate(R.id.action_bottomSheetDialogFragment_to_b2Fragment)

            else -> {
                findNavController().navigate(R.id.action_bottomSheetDialogFragment_to_c2Fragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
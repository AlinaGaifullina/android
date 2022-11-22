package com.example.myapplication.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import com.example.myapplication.AppAdapter
import com.example.myapplication.ItemRepository
import com.example.myapplication.ItemRepository.items
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first) {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: AppAdapter

    private var selectedSort = "id"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFirstBinding.bind(view)

        childFragmentManager.setFragmentResultListener("requestKey", this, FragmentResultListener { requestKey, bundle ->
            selectedSort = bundle.getString("bundleKey").toString()
            when(selectedSort){
                "id" -> {
                    items.sortWith { lhs, rhs -> lhs.id.compareTo(rhs.id) }
                    adapter.notifyDataSetChanged()}
                "id2" -> {
                    items.sortWith { lhs, rhs -> lhs.id.compareTo(rhs.id) }
                    items.reverse()
                    adapter.notifyDataSetChanged()}

                "name" -> {
                    items.sortWith { lhs, rhs -> lhs.name.compareTo(rhs.name) }
                    adapter.notifyDataSetChanged()}

                "name2" -> {
                    items.sortWith { lhs, rhs -> lhs.name.compareTo(rhs.name) }
                    items.reverse()
                    adapter.notifyDataSetChanged()}
            }
        })

        with(binding) {

            val bottomSheetFragment = MyBottomSheetDialogFragment()

            btnSort.setOnClickListener {
                childFragmentManager.setFragmentResult("requestKey2", bundleOf("bundleKey2" to selectedSort))
                bottomSheetFragment.show(childFragmentManager, MyBottomSheetDialogFragment.TAG)
            }

            btnCamera.setOnClickListener {
                val fragment = SecondFragment.newInstance(Bundle())
                parentFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.container, fragment, "SECOND_FRAGMENT_TAG")
                    .commit()
            }

            adapter = AppAdapter(
                items = ItemRepository.items,
            )
            rvItem.adapter = adapter

        }
    }

    companion object{
        const val FIRST_FRAGMENT_TAG = "FIRST_FRAGMENT_TAG"
        fun newInstance(bundle: Bundle) =
            FirstFragment().apply {
                arguments = Bundle().apply {
                    putAll(bundle)
                }
            }
    }
}
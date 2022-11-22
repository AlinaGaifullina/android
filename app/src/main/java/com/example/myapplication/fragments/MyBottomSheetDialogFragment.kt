package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMyBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentMyBottomSheetDialogBinding? = null
    private val binding get() = _binding!!
    private var selectedSort: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_bottom_sheet_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMyBottomSheetDialogBinding.bind(view)

        parentFragmentManager.setFragmentResultListener("requestKey2", this, FragmentResultListener { requestKey2, bundle ->
            selectedSort = bundle.getString("bundleKey2")

            if(selectedSort == "id" || selectedSort == "id2"){
                binding.btnSortId.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple_500))
                binding.btnSortName.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
            } else {
                binding.btnSortName.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple_500))
                binding.btnSortId.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
            }

        })

        with(binding){
            btnSortId.setOnClickListener {
                var result = "id"
                if(selectedSort == "id") result = "id2"
                parentFragmentManager.setFragmentResult("requestKey", bundleOf("bundleKey" to result))
                dismiss()

            }
            btnSortName.setOnClickListener {
                var result = "name"
                if(selectedSort == "name") result = "name2"
                parentFragmentManager.setFragmentResult("requestKey", bundleOf("bundleKey" to result))
                dismiss()
            }
        }
    }


    companion object {
        const val TAG = "MY_BOTTOM_SHEET_DIALOG_FRAGMENT"
    }
}
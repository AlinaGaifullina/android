package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentSecondBinding

class SecondFragment : Fragment(R.layout.fragment_second) {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSecondBinding.bind(view)
        val viewSizes = calculateViewSize()
        changeViewsParams(viewData = viewSizes)
    }


    private fun calculateViewSize(): ViewDimensionsModel {
        val availableWidth = requireContext().resources.displayMetrics.widthPixels
        val availableHeight = requireContext().resources.displayMetrics.heightPixels

        val viewHeight = availableHeight / 2f

        return ViewDimensionsModel(
            horizontalSize = availableWidth,
            verticalSize = viewHeight.toInt()
        )
    }

    private fun changeViewsParams(viewData: ViewDimensionsModel) {
        with(binding){

            (view1.layoutParams as? ConstraintLayout.LayoutParams)?.apply {
                bottomToTop = view3.id
                endToStart = view2.id
                height = viewData.verticalSize
            }

            (view2.layoutParams as? ConstraintLayout.LayoutParams)?.apply {
                startToStart = ConstraintLayout.LayoutParams.UNSET
                topToBottom = ConstraintLayout.LayoutParams.UNSET
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                startToEnd = view1.id
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                bottomToTop = view4.id
                height = viewData.verticalSize
            }
            (view3.layoutParams as? ConstraintLayout.LayoutParams)?.apply {
                topToBottom = view1.id
                endToStart = view4.id
                height = viewData.verticalSize
            }
            (view4.layoutParams as? ConstraintLayout.LayoutParams)?.apply {
                startToStart = ConstraintLayout.LayoutParams.UNSET
                topToBottom = view2.id
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                startToEnd = view3.id
                height = viewData.verticalSize
            }
        }
    }

    companion object{
        fun getInstance() = SecondFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
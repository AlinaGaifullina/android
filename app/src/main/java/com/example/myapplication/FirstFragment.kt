package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first) {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFirstBinding.bind(view)

        val editText = binding.et1.editText
        val editText2 = binding.et2.editText
        if (editText != null && editText2 != null) {
            initTextChangeListeners(editText, editText2)
        }

        binding.button.isEnabled = false
        binding.et2.isEnabled = false

        binding.button.setOnClickListener {
            val fragment = SecondFragment.getInstance()
            parentFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_fragments_container, fragment, "SECOND_FRAGMENT_TAG")
                .commit()
        }
    }

    private fun initTextChangeListeners(et: EditText, et2: EditText) {
        et.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            private var flag = false

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == null || flag) {
                    return
                }

                val goodStr = s.replace(Regex("(\\D*)"), "")
                var result = ""

                for (i in goodStr.indices) {
                    result = when (i) {
                        0 -> result.plus("+7 (9")
                        1 -> result.plus("")
                        2 -> result.plus(goodStr[i])
                        3 -> result.plus(goodStr[i])
                        4 -> result.plus(") ".plus(goodStr[i]))
                        5 -> result.plus(goodStr[i])
                        6 -> result.plus(goodStr[i])
                        7 -> result.plus(" - ".plus(goodStr[i]))
                        8 -> result.plus(goodStr[i])
                        9 -> result.plus(" - ".plus(goodStr[i]))
                        10 -> result.plus(goodStr[i])
                        else -> result
                    }
                }

                flag = true
                val oldSelectionPos = et.selectionStart
                val isEdit = et.selectionStart != et.length()
                et.setText(result)
                if (isEdit) {
                    (if (oldSelectionPos > result.length) result.length else oldSelectionPos).let {
                        et.setSelection(
                            it
                        )
                    }
                } else {
                    et.setSelection(result.length)
                }
                et2.isEnabled = result.length == 22
                flag = false
            }
        })
        et2.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {
                input?.let {
                    binding.button.isEnabled = it.length > 5
                }
            }
        })

    }

    companion object{
        fun getInstance() = FirstFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
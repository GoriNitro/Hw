package com.example.hw.presentation.taskmng.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.hw.R
import com.example.hw.data.local.model.Data
import com.example.hw.data.local.sharedpreferences.Pref
import com.example.hw.databinding.FragmentAddBinding
import com.example.hw.utils.showToast


class AddFragment : Fragment() {
    private val binding: FragmentAddBinding by lazy {
        FragmentAddBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniListener()
    }

    private val list = ArrayList<Data>()

    private fun iniListener() {
        val pref = Pref(requireContext())

        list.clear()
        list.addAll(pref.getObjects())

        binding.button.setOnClickListener {
            val name = binding.editText1.text.toString()
            val title = binding.editText2.text.toString()

            if (name.isNotEmpty() && title.isNotEmpty()) {
                val model = Data(name = name, title = title)
                list.add(model)
                pref.saveData(list)
                findNavController().navigate(R.id.menuFragment)
            } else {
                showToast(getString(R.string.is_empty))
            }
        }
    }
}
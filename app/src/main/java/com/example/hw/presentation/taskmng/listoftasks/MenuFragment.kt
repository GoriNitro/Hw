package com.example.hw.presentation.taskmng.listoftasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw.R
import com.example.hw.data.local.model.Data
import com.example.hw.data.local.sharedpreferences.Pref
import com.example.hw.databinding.FragmentMenuBinding
import com.example.hw.presentation.taskmng.adapter.DataAdapter
import com.example.hw.utils.setLocale
import com.example.hw.utils.showToast

class MenuFragment : Fragment(), TaskView {

    private val viewModel: MainViewModel by viewModels()
    private val binding: FragmentMenuBinding by lazy {
        FragmentMenuBinding.inflate(layoutInflater)
    }
    private val pref = Pref(requireContext())
    private val oldList = ArrayList<Data>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvData.layoutManager = LinearLayoutManager(requireActivity())


        viewModel.dataList.observe(viewLifecycleOwner) { data ->
            if (oldList.isEmpty()) {
                oldList.addAll(data)
            }

            val adapter = DataAdapter(data,
                onItemClick = {
                    showToast(getString(R.string.list_is_empty))
                },
                onClick = {
                    showToast(getString(R.string.item_remove_is_successful))
                })

            binding.rvData.adapter = adapter
            initListener(data, pref)
        }
        viewModel.getList(pref)
    }

    private fun initListener(data: ArrayList<Data>, pref: Pref) {
        binding.btnAdd.setOnClickListener {
            val isListChanged = data != oldList
            if (isListChanged) {
                AlertDialog.Builder(requireContext()).setTitle(getString(R.string.confirm))
                    .setMessage(getString(R.string.list_is_changed_save_changes))

                    .setPositiveButton(getString(R.string.Yes)) { _, _ ->
                        viewModel.saveList(data, pref)
                        findNavController().navigate(R.id.addFragment)
                    }
                    .setNegativeButton(getString(R.string.No)) { dialog, _ ->
                        dialog.dismiss()
                        viewModel.getList(pref)
                    }.create().show()
            } else {
                viewModel.saveList(data, pref)
                findNavController().navigate(R.id.addFragment)
            }
        }
    }

    override fun toastEmptyList() {
        showToast(getString(R.string.list_is_empty))
    }
}

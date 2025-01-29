package com.example.hw.presentation.taskmng.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hw.R
import com.example.hw.data.local.model.Data
import com.example.hw.databinding.ItemDataBinding

class DataAdapter(private var list: ArrayList<Data>,private val onItemClick: () -> Unit, private val onClick:() -> Unit) :
    RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            removeItem(position)
            if (list.isEmpty()){
                onClick()
                Thread.sleep(2000)
                onItemClick()
            }else{
                onClick()
            }
        }
    }

    private fun removeItem(position: Int) {
        val obj = list[position]
        list.remove(obj)
        notifyItemRemoved(position)
    }

    class DataViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private val binding = ItemDataBinding.bind(view)
        fun bind(data: Data) {
            with(binding) {
                titleTextView.text = data.name
                descriptionTextView.text = data.title
            }
        }
    }

}

package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemBinding

class AppAdapter(
    val items: List<Item>
) : RecyclerView.Adapter<AppAdapter.ItemViewHolder>()   {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }


    inner class ItemViewHolder(
        private val binding: ItemBinding,
    ) : RecyclerView.ViewHolder(binding.layoutItem) {

        fun bind(item: Item) {
            with(binding) {
                tvId.text = item.id.toString()
                tvName.text = item.name
            }
        }
    }
}
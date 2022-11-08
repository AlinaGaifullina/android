package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemBinding

class AppAdapter(
    val items: List<Item>,
    private val onItemClick: (position: Int) -> Unit,
) : RecyclerView.Adapter<AppAdapter.ItemViewHolder>()   {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ),
            onItemClick = onItemClick
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }


    inner class ItemViewHolder(
        private val binding: ItemBinding,
        val onItemClick: (position: Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.layoutItem) {

        init {
            with(binding) {
                root.setOnClickListener {
                    onItemClick(adapterPosition)
                }
            }
        }

        fun bind(item: Item) {
            with(binding) {
                tv.text = item.name
                iv.setBackgroundResource(item.image)
            }
        }
    }
}

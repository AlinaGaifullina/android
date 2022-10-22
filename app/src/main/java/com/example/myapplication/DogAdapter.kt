package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.myapplication.databinding.DogItemBinding

class DogAdapter(
    val dogs: List<Dog>,
    private val glide: RequestManager,
    private val onItemClick: (Int) -> Unit,
) : RecyclerView.Adapter<DogAdapter.DogViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder =
        DogViewHolder(
            DogItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ),
            onItemClick = onItemClick,
            glide = glide
        )

    override fun getItemCount(): Int = dogs.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.bind(dogs[position])
    }


    inner class DogViewHolder(
        private val binding: DogItemBinding,
        val onItemClick: (Int) -> Unit,
        private val glide: RequestManager,
    ) : RecyclerView.ViewHolder(binding.layoutItem) {

        init {
            with(binding) {
                root.setOnClickListener {
                    onItemClick(adapterPosition)
                }
            }
        }

        fun bind(dog: Dog) {

            with(binding) {
                tvDogName.text = dog.name

                if (dog.colorID != 0) {
                    layoutItem.setBackgroundColor(
                        ContextCompat.getColor(
                            root.context,
                            dog.colorID
                        )
                    )
                }

                glide
                    .load(dog.imageUrl)
                    .centerCrop()
                    .into(ivDog)
            }
        }
    }
}
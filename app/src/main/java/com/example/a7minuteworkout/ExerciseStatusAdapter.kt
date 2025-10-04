package com.example.a7minuteworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.a7minuteworkout.databinding.ItemExerciseStatusBinding
import androidx.core.graphics.toColorInt

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>):
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemExerciseStatusBinding): RecyclerView.ViewHolder(binding.root){
        val tvItem = binding.tvStatus
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExerciseStatusAdapter.ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false))
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val model : ExerciseModel = items[position]
        holder.tvItem.text = model.getId().toString()

        when{
            model.getIsSelected()->{
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_thin_color_accent_bg)
                holder.tvItem.setTextColor("#212121".toColorInt())
            }
            model.getIsCompleted()->{
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_color_accent_bg)
            }else->{
            holder.tvItem.background =
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_color_gray_bg)
                holder.tvItem.setTextColor("#212121".toColorInt())
            }
        }
    }

    override fun getItemCount(): Int {
        return  items.size
    }



}
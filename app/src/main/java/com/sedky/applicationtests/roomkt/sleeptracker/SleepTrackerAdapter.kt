package com.sedky.applicationtests.roomkt.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sedky.applicationtests.data.model.SleepNight
import com.sedky.applicationtests.databinding.ItemViewBinding

class SleepTrackerAdapter(val clickListener: SleepNightListener) : ListAdapter<SleepNight, SleepTrackerAdapter.SleepTackerViewHolder>
    (SleepNightDiffCallback()) {

    //we have removed getItemCount() because ListAdapter know this items count


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepTackerViewHolder {

        return SleepTackerViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: SleepTackerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,clickListener)

    }


    class SleepTackerViewHolder private constructor(val binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SleepNight, clickListener: SleepNightListener) {
            binding.sleep = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        //this makes the viewHolder is responsibility to know what to layout inflate.
        companion object {
            fun from(parent: ViewGroup): SleepTackerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemViewBinding.inflate(layoutInflater,parent,false)
                return SleepTackerViewHolder(binding)
            }
        }
    }


    class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
        override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
            return oldItem.nightId == newItem.nightId
        }

        override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
           return oldItem == newItem
        }

    }
    class SleepNightListener(val clickListener : (sleepId :Long) -> Unit){
        fun onClick(night : SleepNight) = clickListener(night.nightId)
    }

}
package com.sedky.applicationtests.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sedky.applicationtests.data.model.SleepNight
import com.sedky.applicationtests.databinding.ItemViewBinding

class AnyAdapter : ListAdapter<SleepNight, AnyAdapter.AnyViewHolder>(AnyDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnyViewHolder {
        return AnyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AnyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class AnyViewHolder private constructor(val binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val textView2: TextView = binding.qualityString

        fun bind(item: SleepNight) {
            binding.sleep = item
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): AnyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemViewBinding.inflate(layoutInflater, parent, false)
                return AnyViewHolder(binding)
            }
        }
    }

    class AnyDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
        override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
            return oldItem.nightId == newItem.nightId
        }

        override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
            return oldItem == newItem
        }

    }

}
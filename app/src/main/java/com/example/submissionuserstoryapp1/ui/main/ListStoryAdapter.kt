package com.example.submissionuserstoryapp1.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionuserstoryapp1.data.remote.response.ListStoryItem
import com.example.submissionuserstoryapp1.database.StoryItem
import com.example.submissionuserstoryapp1.databinding.StoryRowBinding

class ListStoryAdapter :
    PagingDataAdapter<StoryItem, ListStoryAdapter.ListStoryViewHolder>(DIFF_CALLBACK) {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListStoryViewHolder(val binding: StoryRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: StoryItem) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(data)
            }
            binding.tvStory.text = data.name
            Glide.with(itemView)
                .load(data.photoUrl)
                .into(binding.ivStory)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListStoryViewHolder {
        val binding = StoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListStoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListStoryViewHolder, position: Int) {
        val data = getItem(position)
        data?.let { holder.bind(it) }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: StoryItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryItem>() {
            override fun areItemsTheSame(oldItem: StoryItem, newItem: StoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoryItem, newItem: StoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
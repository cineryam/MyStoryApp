package com.example.mystoryapp.ui

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import androidx.core.util.Pair
import com.example.mystoryapp.data.Story
import com.example.mystoryapp.databinding.ItemStoryBinding
import com.example.mystoryapp.ui.activity.StoryDetailActivity

class StoriesAdapter : PagingDataAdapter<Story, StoriesAdapter.StoriesViewHolder>(DIFF_CALLBACK) {
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class StoriesViewHolder(internal val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Story?) {
            if (item != null) {
                binding.root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(item)
                }

                binding.apply {
                    Glide.with(itemView)
                        .load(item.photoUrl)
                        .into(ivItemPhoto)
                    tvItemName.text = item.name
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Story)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
        val view = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(story)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, StoryDetailActivity::class.java).apply {
                putExtra("id", story?.id)
                putExtra("name", story?.name)
                putExtra("photo_url", story?.photoUrl)
                putExtra("description", story?.description)
            }
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                holder.itemView.context as Activity,
                Pair(holder.binding.ivItemPhoto, "photo"),
                Pair(holder.binding.tvItemName, "description")
            )
            context.startActivity(intent, options.toBundle())
        }
    }
}
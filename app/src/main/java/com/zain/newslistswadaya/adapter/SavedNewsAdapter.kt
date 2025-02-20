package com.zain.newslistswadaya.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zain.newslistswadaya.databinding.ItemListSavedNewsBinding
import com.zain.newslistswadaya.local.SavedNewsEntity

class SavedNewsAdapter(private val onClick: (SavedNewsEntity) -> Unit) :
    RecyclerView.Adapter<SavedNewsAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<SavedNewsEntity>() {
        override fun areItemsTheSame(
            oldItem: SavedNewsEntity,
            newItem: SavedNewsEntity
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: SavedNewsEntity,
            newItem: SavedNewsEntity
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding: ItemListSavedNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(news: SavedNewsEntity) {
            binding.tvTitleNews.text = news.title
            Glide.with(itemView.context).load(news.urlToImage).into(binding.ivNews)

            itemView.setOnClickListener { onClick(news) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListSavedNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = differ.currentList[position]
        holder.bind(result)
    }

    override fun getItemCount(): Int = differ.currentList.size
    fun setData(news: List<SavedNewsEntity>) {
        differ.submitList(news)
    }
}
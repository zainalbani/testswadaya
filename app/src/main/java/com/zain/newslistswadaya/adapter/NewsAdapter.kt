package com.zain.newslistswadaya.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zain.newslistswadaya.databinding.ItemListNewsBigBinding
import com.zain.newslistswadaya.databinding.ItemListNewsSmallBinding
import com.zain.newslistswadaya.response.ArticlesItem

class NewsAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<ArticlesItem>() {
        override fun areItemsTheSame(
            oldItem: ArticlesItem,
            newItem: ArticlesItem
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: ArticlesItem,
            newItem: ArticlesItem
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, differCallback)

    override fun getItemViewType(position: Int): Int {
        return differ.currentList[position].type
    }

    inner class BigViewHolder(private val binding: ItemListNewsBigBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ArticlesItem) {
            binding.tvNameNews.text = item.source?.name
            binding.tvTitleNews.text = item.title
            binding.tvAuthorNews.text = item.author
            binding.tvDescriptionNews.text = item.description
            binding.tvPublishedAt.text = item.publishedAt
            Glide.with(itemView.context)
                .load(item.urlToImage)
                .into(binding.ivNews)
        }
    }

    inner class SmallViewHolder(private val binding: ItemListNewsSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ArticlesItem) {
            binding.tvNameNews.text = item.source?.name
            binding.tvTitleNews.text = item.title
            binding.tvAuthorNews.text = item.author
            binding.tvDescriptionNews.text = item.description
            binding.tvPublishedAt.text = item.publishedAt
            Glide.with(itemView.context)
                .load(item.urlToImage)
                .into(binding.ivNews)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ArticlesItem.TYPE_BIG) {
            val binding = ItemListNewsBigBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            BigViewHolder(binding)
        } else {
            val binding = ItemListNewsSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SmallViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val result = differ.currentList[position]
        when (holder) {
            is BigViewHolder -> holder.bind(result)
            is SmallViewHolder -> holder.bind(result)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(data: List<ArticlesItem>) {
        differ.submitList(data)
    }

}
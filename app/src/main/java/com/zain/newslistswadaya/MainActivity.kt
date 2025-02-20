package com.zain.newslistswadaya

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.distinctUntilChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zain.newslistswadaya.adapter.NewsAdapter
import com.zain.newslistswadaya.databinding.ActivityMainBinding
import com.zain.newslistswadaya.response.ArticlesItem
import com.zain.newslistswadaya.response.BaseResponse
import com.zain.newslistswadaya.utils.ArrangeItemsUtils
import com.zain.newslistswadaya.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getNewsFetch()
        searchNewsFetch()



    }

    private fun searchNewsFetch() {
        val searchView = binding.searchView
        val searchBar = binding.searchBar

        searchView.setupWithSearchBar(searchBar)
        searchView.editText.setOnEditorActionListener { _, _, _ ->
            searchBar.setText(searchView.text)
            searchView.hide()
            val news = searchView.text.toString()
            viewModel.searchNews(news)
            false
        }
        viewModel.searchResult.distinctUntilChanged().observe(this, Observer {
            when (it) {
                is BaseResponse.Loading -> {
                    binding.skeletonLoading.visibility = View.VISIBLE
                    binding.skeletonLoading.startShimmer()
                    binding.rvNews.visibility = View.GONE
                    binding.searchBar.visibility = View.GONE
                }

                is BaseResponse.Success -> {
                    binding.skeletonLoading.visibility = View.GONE
                    binding.skeletonLoading.stopShimmer()
                    binding.rvNews.visibility = View.VISIBLE
                    binding.searchBar.visibility = View.VISIBLE


                    adapter = NewsAdapter()
                    binding.rvNews.adapter = adapter
                    val layoutManager = GridLayoutManager(this, 2)
                    binding.rvNews.layoutManager = layoutManager

                    layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return if (adapter.getItemViewType(position) == ArticlesItem.TYPE_BIG) 2 else 1
                        }
                    }

                    val arrangedItems = ArrangeItemsUtils.arrangeItems(it.data?.articles)

                    adapter.setData(arrangedItems)

                }

                is BaseResponse.Error -> {
                    binding.skeletonLoading.visibility = View.VISIBLE
                    binding.skeletonLoading.startShimmer()
                    binding.rvNews.visibility = View.GONE
                    binding.searchBar.visibility = View.GONE


                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Error")
                    builder.setMessage(it.msg)

                    builder.setPositiveButton("OK") { _, _ ->

                    }
                    val dialog = builder.create()
                    dialog.show()
                }

                else -> {

                }
            }
        })
    }

    private fun getNewsFetch() {
        viewModel.getNews()
        viewModel.newsResult.observe(this){
            when (it) {
                is BaseResponse.Loading -> {
                    binding.skeletonLoading.visibility = View.VISIBLE
                    binding.skeletonLoading.startShimmer()
                    binding.rvNews.visibility = View.GONE
                    binding.searchBar.visibility = View.GONE

                }

                is BaseResponse.Success -> {

                    binding.skeletonLoading.visibility = View.GONE
                    binding.skeletonLoading.stopShimmer()
                    binding.rvNews.visibility = View.VISIBLE
                    binding.searchBar.visibility = View.VISIBLE


                    adapter = NewsAdapter()
                    binding.rvNews.adapter = adapter
                    val layoutManager = GridLayoutManager(this, 2)
                    binding.rvNews.layoutManager = layoutManager

                    layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return if (adapter.getItemViewType(position) == ArticlesItem.TYPE_BIG) 2 else 1
                        }
                    }

                    val arrangedItems = ArrangeItemsUtils.arrangeItems(it.data?.articles)

                    adapter.setData(arrangedItems)



                }

                is BaseResponse.Error -> {

                    binding.skeletonLoading.visibility = View.VISIBLE
                    binding.skeletonLoading.startShimmer()
                    binding.rvNews.visibility = View.GONE
                    binding.searchBar.visibility = View.GONE


                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Error")
                    builder.setMessage(it.msg)

                    builder.setPositiveButton("OK") { _, _ ->

                    }
                    val dialog = builder.create()
                    dialog.show()
                }

                else -> {
                }
            }
        }
    }
}
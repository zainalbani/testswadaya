package com.zain.newslistswadaya

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.zain.newslistswadaya.adapter.SavedNewsAdapter
import com.zain.newslistswadaya.databinding.ActivitySavedNewsBinding
import com.zain.newslistswadaya.local.AppDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedNewsActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySavedNewsBinding
    private lateinit var adapter : SavedNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDatabase.getDatabase(this)
        val dao = db.savedNewsDao()

        CoroutineScope(Dispatchers.IO).launch {
            val savedNewsList = dao.getSavedNews()
            savedNewsList.let{
                adapter = SavedNewsAdapter{data ->

                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data.url))
                    startActivity(intent)

                }
                binding.rvSavedNews.adapter = adapter
                val layoutManager = LinearLayoutManager(this@SavedNewsActivity)
                binding.rvSavedNews.layoutManager = layoutManager

                adapter.setData(it)
            }
        }
        binding.backButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

}
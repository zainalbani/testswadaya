package com.zain.newslistswadaya

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.zain.newslistswadaya.databinding.ActivityDetailBinding
import com.zain.newslistswadaya.local.AppDatabase
import com.zain.newslistswadaya.local.SavedNewsEntity
import com.zain.newslistswadaya.response.ArticlesItem
import com.zain.newslistswadaya.utils.DateFormatUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<ArticlesItem>("data")

        data?.let {

            val db = AppDatabase.getDatabase(this)
            val dao = db.savedNewsDao()

            CoroutineScope(Dispatchers.IO).launch {

                val existingNews = dao.checkDuplicate(it.title.toString())
                if (existingNews == null){
                    val savedNews = SavedNewsEntity(it.title.toString(), it.urlToImage.toString(), it.url.toString())
                    dao.insertSavedNews(savedNews)
                }

            }

            binding.tvTitleDetail.text = it.title
            binding.tvNameDetail.text = it.source?.name
            binding.tvAuthorDetail.text = it.author
            binding.tvPublishedAtDetail.text =
                "Published at " + it.publishedAt?.let { DateFormatUtils.formatDate(it) }
            binding.tvContent.text = it.description
            Glide.with(this)
                .load(it.urlToImage)
                .into(binding.ivUrlImage)

            binding.btnReadMore.setOnClickListener {view->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
                startActivity(intent)
            }

        }
        binding.backButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}
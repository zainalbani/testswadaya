package com.zain.newslistswadaya.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_news")
data class SavedNewsEntity(
    @PrimaryKey val title: String,
    val urlToImage: String,
    val url: String
)
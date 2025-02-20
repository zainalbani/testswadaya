package com.zain.newslistswadaya.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SavedNewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSavedNews(news: SavedNewsEntity)

    @Query("SELECT * FROM saved_news WHERE title = :title LIMIT 1")
    fun checkDuplicate(title: String): SavedNewsEntity?

    @Query("SELECT * FROM saved_news")
    fun getSavedNews(): List<SavedNewsEntity>

}
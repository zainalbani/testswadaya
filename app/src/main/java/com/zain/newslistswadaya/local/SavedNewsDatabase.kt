package com.zain.newslistswadaya.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SavedNewsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedNewsDao(): SavedNewsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "saved_news_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
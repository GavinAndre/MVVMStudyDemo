package com.gavinandre.mvvmstudydemo.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gavinandre.mvvmstudydemo.model.data.Article
import com.gavinandre.mvvmstudydemo.model.local.dao.PaoDao

@Database(entities = [Article::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun paoDao(): PaoDao
    
    companion object {
        
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app.db"
            ).build()
    }
    
}
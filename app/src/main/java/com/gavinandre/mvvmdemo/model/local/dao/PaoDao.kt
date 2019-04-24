package com.gavinandre.mvvmdemo.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gavinandre.mvvmdemo.model.data.Article
import io.reactivex.Single

@Dao
interface PaoDao{
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insetAll(articles: List<Article>)
    
    @Query("SELECT * FROM Articles WHERE articleid= :id")
    fun getArticleById(id:Int): Single<Article>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article :Article)
    
}
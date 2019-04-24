package com.gavinandre.mvvmdemo.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey
    @ColumnInfo(name = "articleid")
    var id: Int = 0,
    var title: String? = null,
    var content: String? = null,
    var readme: String? = null,
    @SerializedName("describe")
    var description: String? = null,
    var click: Int = 0,
    var channel: Int = 0,
    var comments: Int = 0,
    var stow: Int = 0,
    var upvote: Int = 0,
    var downvote: Int = 0,
    var url: String? = null,
    var pubDate: String? = null,
    var thumbnail: String? = null
)
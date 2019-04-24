package com.gavinandre.mvvmdemo.model.repository

import com.gavinandre.mvvmdemo.model.local.dao.PaoDao
import com.gavinandre.mvvmdemo.model.remote.PaoService

class PaoRepo(private val remote: PaoService, private val local: PaoDao) {
    
    private val TAG = PaoRepo::class.java.simpleName
    
    //首先查看本地数据库是否存在该篇文章
    fun getArticleDetail(id: Int) =
        local.getArticleById(id)
            .onErrorResumeNext {
                //本地数据库不存在，会抛出EmptyResultSetException
                //转而获取网络数据,成功后保存到数据库
                remote.getArticleDetail(id)
                    .doOnSuccess {
                        local.insertArticle(it)
                    }
            }
}
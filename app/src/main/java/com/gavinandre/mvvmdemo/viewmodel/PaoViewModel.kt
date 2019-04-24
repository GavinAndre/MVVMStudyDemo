package com.gavinandre.mvvmdemo.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.gavinandre.mvvmdemo.helper.Utils
import com.gavinandre.mvvmdemo.helper.async
import com.gavinandre.mvvmdemo.model.remote.PaoService

class PaoViewModel(private val remote: PaoService) {
    
    private val TAG = PaoViewModel::class.java.simpleName
    
    val loading = ObservableBoolean(false)//加载
    val content = ObservableField<String>()//内容
    val title = ObservableField<String>()//标题
    val error = ObservableField<Throwable>()//错误Toast
    
    fun loadArticle() {
        //为了简单起见这里先写个默认的id
        remote.getArticleDetail(8773)
            .async(1000)
            .doOnSubscribe { startLoad() }
            .doOnSuccess {
                title.set(it.title)
                it.content?.apply {
                    val articleContent = Utils.processImgSrc(this)
                    content.set(articleContent)
                }
            }
            .doOnError { error.set(it) }
            .doAfterTerminate { stopLoad() }
            .subscribe { _, _ -> }
    }
    
    fun startLoad() = loading.set(true)
    fun stopLoad() = loading.set(false)
}
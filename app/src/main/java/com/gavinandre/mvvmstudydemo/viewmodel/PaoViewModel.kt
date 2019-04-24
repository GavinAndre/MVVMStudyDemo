package com.gavinandre.mvvmstudydemo.viewmodel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.gavinandre.mvvmstudydemo.helper.Utils
import com.gavinandre.mvvmstudydemo.helper.async
import com.gavinandre.mvvmstudydemo.model.data.Article
import com.gavinandre.mvvmstudydemo.model.repository.PaoRepo
import io.reactivex.Single

class PaoViewModel(private val repo: PaoRepo) : ViewModel() {
    
    private val TAG = PaoViewModel::class.java.simpleName
    
    val loading = ObservableBoolean(false)//加载
    val content = ObservableField<String>()//内容
    val title = ObservableField<String>()//标题
    val error = ObservableField<Throwable>()//错误Toast
    
    fun loadArticle(): Single<Article> =
        //为了简单起见这里先写个默认的id
        repo.getArticleDetail(8773)
            .async(1000)
            .doOnSubscribe { startLoad() }
            .doOnSuccess {
                Log.i(TAG, "doOnSuccess2: ${Thread.currentThread().name}")
                title.set(it.title)
                it.content?.apply {
                    val articleContent = Utils.processImgSrc(this)
                    content.set(articleContent)
                }
            }
            .doOnError { error.set(it) }
            .doAfterTerminate { stopLoad() }
    
    
    fun startLoad() = loading.set(true)
    fun stopLoad() = loading.set(false)
}
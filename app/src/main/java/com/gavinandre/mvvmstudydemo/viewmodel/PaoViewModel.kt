package com.gavinandre.mvvmstudydemo.viewmodel

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gavinandre.mvvmstudydemo.helper.Utils
import com.gavinandre.mvvmstudydemo.helper.async
import com.gavinandre.mvvmstudydemo.model.data.Article
import com.gavinandre.mvvmstudydemo.model.repository.PaoRepo
import io.reactivex.Single

class PaoViewModel(private val repo: PaoRepo) : ViewModel(), DefaultLifecycleObserver {
    
    private val TAG = PaoViewModel::class.java.simpleName
    
    val loading = MutableLiveData<Boolean>()//加载
    val content = MutableLiveData<String>()//内容
    val title = MutableLiveData<String>()//标题
    val error = MutableLiveData<Throwable>()//错误Toast
    
    init {
        loading.value = false
    }
    
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.i(TAG, "BasePresenter.onCreate $javaClass")
    }
    
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.i(TAG, "BasePresenter.onDestroy $javaClass")
    }
    
    fun loadArticle(): Single<Article> =
        //为了简单起见这里先写个默认的id
        repo.getArticleDetail(8773)
            .async(1000)
            .doOnSubscribe { startLoad() }
            .doOnSuccess {
                Log.i(TAG, "doOnSuccess2: ${Thread.currentThread().name}")
                title.value = it.title
                it.content?.apply {
                    val articleContent = Utils.processImgSrc(this)
                    content.value = (articleContent)
                }
            }
            .doOnError { error.value = (it) }
            .doAfterTerminate { stopLoad() }
    
    private fun startLoad() {
        loading.value = true
    }
    
    private fun stopLoad() {
        loading.value = false
    }
}
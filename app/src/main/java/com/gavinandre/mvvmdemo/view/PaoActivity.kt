package com.gavinandre.mvvmdemo.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.gavinandre.mvvmdemo.R
import com.gavinandre.mvvmdemo.databinding.ActivityPaoBinding
import com.gavinandre.mvvmdemo.helper.setMarkdown
import com.gavinandre.mvvmdemo.model.remote.PaoService
import com.gavinandre.mvvmdemo.viewmodel.PaoViewModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import us.feras.mdv.MarkdownView

class PaoActivity : AppCompatActivity() {
    
    lateinit var mBinding: ActivityPaoBinding
    lateinit var mViewMode: PaoViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_pao)
        setSupportActionBar(mBinding.toolbar)
        
        val remote = Retrofit.Builder()
            .baseUrl("http://api.jcodecraeer.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(PaoService::class.java)
        
        mViewMode = PaoViewModel(remote)
        mBinding.paoViewModel = mViewMode
    }
    
    @BindingAdapter(value = ["markdown"])
    fun bindMarkDown(v: MarkdownView, markdown: String?) {
        markdown?.let {
            v.setMarkdown(it)
        }
    }
    
    @BindingAdapter(value = ["toast"])
    fun bindToast(v: View, msg: Throwable) {
        Toast.makeText(v.context, msg.message, Toast.LENGTH_SHORT).show()
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            menuInflater.inflate(R.menu.detail_menu, it)
        }
        return super.onCreateOptionsMenu(menu)
    }
    
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            when (it.itemId) {
                R.id.action_refresh -> mViewMode.loadArticle()
                else -> {
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    
}

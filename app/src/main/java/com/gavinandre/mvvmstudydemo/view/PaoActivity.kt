package com.gavinandre.mvvmstudydemo.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.gavinandre.mvvmstudydemo.R
import com.gavinandre.mvvmstudydemo.databinding.ActivityPaoBinding
import com.gavinandre.mvvmstudydemo.helper.disposableOnDestroy
import com.gavinandre.mvvmstudydemo.model.local.AppDatabase
import com.gavinandre.mvvmstudydemo.model.remote.PaoService
import com.gavinandre.mvvmstudydemo.model.repository.PaoRepo
import com.gavinandre.mvvmstudydemo.viewmodel.PaoViewModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PaoActivity : AppCompatActivity() {
    
    private val TAG = PaoActivity::class.java.simpleName
    
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
        val local = AppDatabase.getInstance(applicationContext).paoDao()
        val repo = PaoRepo(remote, local)
        
        mViewMode = PaoViewModel(repo)
        mBinding.paoViewModel = mViewMode
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
                    .disposableOnDestroy(this)
                    .subscribe({}, { error -> dispatchError(error) })
                else -> {
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    
    //依旧不依赖于具体实现，可以是Toast/Dialog/Snackbar等等
    private fun dispatchError(error: Throwable?) {
        error?.let {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        }
    }
}
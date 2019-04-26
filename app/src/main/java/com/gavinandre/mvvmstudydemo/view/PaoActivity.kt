package com.gavinandre.mvvmstudydemo.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.gavinandre.mvvmstudydemo.R
import com.gavinandre.mvvmstudydemo.databinding.ActivityPaoBinding
import com.gavinandre.mvvmstudydemo.helper.disposableOnDestroy
import com.gavinandre.mvvmstudydemo.helper.setMarkdown
import com.gavinandre.mvvmstudydemo.viewmodel.PaoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import us.feras.mdv.MarkdownView

class PaoActivity : AppCompatActivity() {
    
    private val TAG = PaoActivity::class.java.simpleName
    
    private val mBinding: ActivityPaoBinding by lazy {
        DataBindingUtil.setContentView<ActivityPaoBinding>(this, R.layout.activity_pao)
    }
    private val mViewMode: PaoViewModel by viewModel()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(mBinding.toolbar)
        mBinding.paoViewModel = mViewMode
        mBinding.lifecycleOwner = this
        lifecycle.addObserver(mViewMode)
        Log.i(TAG, "onCreate: ")
    }
    
    object PaoActivityBindingAdapter {
        @BindingAdapter(value = ["markdown"])
        @JvmStatic
        fun bindMarkDown(v: MarkdownView, markdown: String?) {
            markdown?.let {
                v.setMarkdown(it)
            }
        }
        
        @BindingAdapter(value = ["toast"])
        @JvmStatic
        fun bindToast(v: View, msg: Throwable?) {
            msg?.let {
                Toast.makeText(v.context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
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
//                    .subscribe({}, { error -> dispatchError(error) })
                    .subscribe { _, _ -> }
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

package com.gavinandre.mvvmdemo.helper

import android.view.View
import android.widget.Toast
import androidx.databinding.BindingAdapter
import us.feras.mdv.MarkdownView

@BindingAdapter(value = ["markdown"])
fun bindMarkDown(v: MarkdownView, markdown: String?) {
    markdown?.let {
        v.setMarkdown(it)
    }
}

@BindingAdapter(value = ["toast"])
fun bindToast(v: View, msg:Throwable ?){
    msg?.let {
        Toast.makeText(v.context,it.message,Toast.LENGTH_SHORT).show()
    }
}
package com.gavinandre.mvvmdemo.helper

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import us.feras.mdv.MarkdownView
import java.util.concurrent.TimeUnit

fun MarkdownView.setMarkdown(markdown: String?) {
    loadMarkdown(markdown, "file:///android_asset/markdown.css")
}

fun <T> Single<T>.async(withDelay: Long = 0): Single<T> =
    this.subscribeOn(Schedulers.io())
        .delay(withDelay, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
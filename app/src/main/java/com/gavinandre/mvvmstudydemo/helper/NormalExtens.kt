package com.gavinandre.mvvmstudydemo.helper

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.SingleSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.lifecycle.autoDisposable
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

fun <T> Single<T>.disposableOnDestroy(owner: LifecycleOwner): SingleSubscribeProxy<T> {
    return this.autoDisposable(
        AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)
    )
}
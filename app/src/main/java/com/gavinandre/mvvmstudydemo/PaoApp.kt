package com.gavinandre.mvvmstudydemo

import android.app.Application
import com.gavinandre.mvvmstudydemo.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PaoApp : Application() {
    
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@PaoApp)
            modules(appModule)
        }
    }
}
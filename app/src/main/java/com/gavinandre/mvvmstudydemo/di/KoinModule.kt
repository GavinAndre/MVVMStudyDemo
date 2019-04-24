package com.gavinandre.mvvmstudydemo.di

import com.gavinandre.mvvmstudydemo.helper.Constants
import com.gavinandre.mvvmstudydemo.model.local.AppDatabase
import com.gavinandre.mvvmstudydemo.model.remote.PaoService
import com.gavinandre.mvvmstudydemo.model.repository.PaoRepo
import com.gavinandre.mvvmstudydemo.viewmodel.PaoViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { PaoViewModel(get()) }
    //or use reflection
//    viewModel<PaoViewModel>()
}

val repoModule = module {
    factory { PaoRepo(get(), get()) }
    //其实就是
    //factory <PaoRepo> { PaoRepo(get<PaoService>(), get<PaoDao>())  }
}

val remoteModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(Constants.HOST_API)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<PaoService> { get<Retrofit>().create(PaoService::class.java) }
}

val localModule = module {
    single { AppDatabase.getInstance(androidApplication()) }
    single { get<AppDatabase>().paoDao() }
}

val appModule = listOf(viewModelModule, repoModule, remoteModule, localModule)
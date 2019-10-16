package com.fernandez.rico.jetpatck.ui

import android.app.Application
import com.fernandez.rico.jetpatck.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application()
{
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(serviceModule,database,useCaseModule,repositoryModule,paging,viewModelModule))
        }

    }
}
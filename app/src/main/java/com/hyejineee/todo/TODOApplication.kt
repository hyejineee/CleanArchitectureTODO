package com.hyejineee.todo

import android.app.Application
import org.koin.core.context.startKoin

class TODOApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        // TODO : koin trigger
        startKoin {

        }
    }
}
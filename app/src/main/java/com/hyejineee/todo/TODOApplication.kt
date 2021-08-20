package com.hyejineee.todo

import android.app.Application
import com.hyejineee.todo.DI.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TODOApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        // TODO : koin trigger
        startKoin {
            androidLogger(level = Level.ERROR)
            androidContext(this@TODOApplication)

            modules(appModule)
        }
    }
}
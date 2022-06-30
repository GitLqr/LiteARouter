package com.gitlqr.litearouter.androidxdemo

import android.app.Application
import com.gitlqr.litearouter.launcher.LiteARouter

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LiteARouter.openDebug()
        LiteARouter.openLog()
        LiteARouter.init(this)
    }
}
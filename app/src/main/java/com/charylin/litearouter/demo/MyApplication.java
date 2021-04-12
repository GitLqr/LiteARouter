package com.charylin.litearouter.demo;

import android.app.Application;

import com.charylin.litearouter.launcher.LiteARouter;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LiteARouter.openDebug();
        LiteARouter.openLog();
        LiteARouter.init(this);
    }
}

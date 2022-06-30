package com.gitlqr.litearouter.demo;

import android.app.Application;

import com.gitlqr.litearouter.launcher.LiteARouter;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LiteARouter.openDebug();
        LiteARouter.openLog();
        LiteARouter.init(this);
    }
}

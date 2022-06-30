package com.gitlqr.litearouter.compat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * ActivityCompat 兼容层
 *
 * @author LQR
 * @since 2022/6/30
 */
public abstract class ActivityCompat {

    private static ActivityCompat activityCompatAndroidX;
    private static ActivityCompat activityCompatSupport;

    public static ActivityCompat getInstance() {
        if (CompatConfig.DEPENDENCY_ANDROIDX) {
            if (activityCompatAndroidX == null) {
                activityCompatAndroidX = new ActivityCompatAndroidX();
            }
            return activityCompatAndroidX;
        } else if (CompatConfig.DEPENDENCY_SUPPORT) {
            if (activityCompatSupport == null) {
                activityCompatSupport = new ActivityCompatSupport();
            }
            return activityCompatSupport;
        }
        return null;
    }

    public abstract void startActivity(Context context, Intent intent, Bundle options);

    public abstract void startActivityForResult(Activity activity, Intent intent, int requestCode, Bundle options);
}

package com.gitlqr.litearouter.compat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * AndroidX 的 ActivityCompat 实现
 *
 * @author LQR
 * @since 2022/6/30
 */
public class ActivityCompatAndroidX extends ActivityCompat {

    @Override
    public void startActivity(Context context, Intent intent, Bundle options) {
        androidx.core.app.ActivityCompat.startActivity(context, intent, options);
    }

    @Override
    public void startActivityForResult(Activity activity, Intent intent, int requestCode, Bundle options) {
        androidx.core.app.ActivityCompat.startActivityForResult(activity, intent, requestCode, options);
    }
}

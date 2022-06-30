package com.gitlqr.litearouter.compat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Support 的 ActivityCompat 实现
 *
 * @author LQR
 * @since 2022/6/30
 */
public class ActivityCompatSupport extends ActivityCompat {

    @Override
    public void startActivity(Context context, Intent intent, Bundle options) {
        android.support.v4.app.ActivityCompat.startActivity(context, intent, options);
    }

    @Override
    public void startActivityForResult(Activity activity, Intent intent, int requestCode, Bundle options) {
        android.support.v4.app.ActivityCompat.startActivityForResult(activity, intent, requestCode, options);
    }
}

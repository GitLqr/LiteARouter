package com.gitlqr.litearouter.compat;

import android.app.Fragment;
import android.os.Bundle;

/**
 * AndroidX 的 FragmentCompat 实现
 *
 * @author LQR
 * @since 2022/6/30
 */
public class FragmentCompatAndroidX extends FragmentCompat {

    @Override
    public void setArguments(Object fragment, Bundle args) {
        if (fragment instanceof Fragment) {
            ((Fragment) fragment).setArguments(args);
        } else if (fragment instanceof androidx.fragment.app.Fragment) {
            ((androidx.fragment.app.Fragment) fragment).setArguments(args);
        }
    }
}

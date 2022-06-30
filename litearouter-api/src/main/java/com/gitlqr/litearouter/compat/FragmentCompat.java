package com.gitlqr.litearouter.compat;

import android.os.Bundle;

/**
 * Fragment 兼容层
 *
 * @author LQR
 * @since 2022/6/30
 */
public abstract class FragmentCompat {

    private static FragmentCompat fragmentCompatAndroidX;
    private static FragmentCompat fragmentCompatSupport;

    public static FragmentCompat getInstance() {
        if (CompatConfig.DEPENDENCY_ANDROIDX) {
            if (fragmentCompatAndroidX == null) {
                fragmentCompatAndroidX = new FragmentCompatAndroidX();
            }
            return fragmentCompatAndroidX;
        } else if (CompatConfig.DEPENDENCY_SUPPORT) {
            if (fragmentCompatSupport == null) {
                fragmentCompatSupport = new FragmentCompatSupport();
            }
            return fragmentCompatSupport;
        }
        return null;
    }

    public abstract void setArguments(Object fragment, Bundle args);
}
package com.gitlqr.litearouter.compat;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Suppoort 的 FragmentCompat 实现
 *
 * @author LQR
 * @since 2022/6/30
 */
public class FragmentCompatSupport extends FragmentCompat {

    @Override
    public void setArguments(Object fragment, Bundle args) {
        if (fragment instanceof Fragment) {
            ((Fragment) fragment).setArguments(args);
        } else if (fragment instanceof android.support.v4.app.Fragment) {
            ((android.support.v4.app.Fragment) fragment).setArguments(args);
        }
    }
}

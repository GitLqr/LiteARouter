package com.charylin.litearouter.launcher;

import android.content.Context;
import android.net.Uri;

import com.charylin.litearouter.facade.Postcard;
import com.charylin.litearouter.facade.callback.NavigationCallback;
import com.charylin.litearouter.facade.template.ILogger;
import com.charylin.litearouter.facade.template.IRouteGroup;
import com.charylin.litearouter.utils.Consts;

/**
 * @author LQR
 * @time 2021/4/9
 * @desc LiteARouter facade
 */
public class LiteARouter {

    private volatile static boolean hasInit = false;
    public static ILogger logger;

    private static final class Holder {
        private static final LiteARouter INSTANCE = new LiteARouter();
    }

    private LiteARouter() {
    }

    public static void init(Context context) {
        if (!hasInit) {
            logger = _LiteARouter.logger;
            logger.info(Consts.TAG, "LiteARouter init start.");
            hasInit = _LiteARouter.init(context.getApplicationContext());
            if (hasInit) {
                _LiteARouter.afterInit();
            }
            logger.info(Consts.TAG, "LiteARouter init over.");
        }
    }

    public static LiteARouter getInstance() {
        if (!hasInit) {
            throw new RuntimeException(Consts.TAG + "Init::Invoke init(context) first!");
        } else {
            return Holder.INSTANCE;
        }
    }

    public static synchronized void openDebug() {
        _LiteARouter.openDebug();
    }

    public static boolean isDebuggable() {
        return _LiteARouter.isDebuggable();
    }

    public static synchronized void openLog() {
        _LiteARouter.openLog();
    }

    public synchronized void destroy() {
        _LiteARouter.destroy();
        hasInit = false;
    }

    /**
     * Build the roadmap, draw a postcard.
     *
     * @param path Where you go.
     */
    public Postcard build(String path) {
        return _LiteARouter.getInstance().build(path);
    }

    /**
     * Build the roadmap, draw a postcard.
     *
     * @param path  Where you go.
     * @param group The group of path.
     */
    @Deprecated
    public Postcard build(String path, String group) {
        return _LiteARouter.getInstance().build(path, group);
    }

    /**
     * Build the roadmap, draw a postcard.
     *
     * @param url the path
     */
    public Postcard build(Uri url) {
        return _LiteARouter.getInstance().build(url);
    }

    /**
     * Launch the navigation.
     *
     * @param mContext    .
     * @param postcard    .
     * @param requestCode Set for startActivityForResult
     * @param callback    cb
     */
    public Object navigation(Context mContext, Postcard postcard, int requestCode, NavigationCallback callback) {
        return _LiteARouter.getInstance().navigation(mContext, postcard, requestCode, callback);
    }

    /**
     * Add route group dynamic.
     *
     * @param group route group.
     * @return add result.
     */
    public boolean addRouteGroup(IRouteGroup group) {
        return _LiteARouter.getInstance().addRouteGroup(group);
    }
}

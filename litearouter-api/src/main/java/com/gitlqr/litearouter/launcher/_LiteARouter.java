package com.gitlqr.litearouter.launcher;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.gitlqr.litearouter.compat.ActivityCompat;
import com.gitlqr.litearouter.compat.FragmentCompat;
import com.gitlqr.litearouter.core.LogisticsCenter;
import com.gitlqr.litearouter.facade.Postcard;
import com.gitlqr.litearouter.facade.callback.NavigationCallback;
import com.gitlqr.litearouter.facade.model.RouteMeta;
import com.gitlqr.litearouter.facade.template.ILogger;
import com.gitlqr.litearouter.facade.template.IRouteGroup;
import com.gitlqr.litearouter.thread.DefaultPoolExecutor;
import com.gitlqr.litearouter.utils.Consts;
import com.gitlqr.litearouter.utils.DefaultLogger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

final class _LiteARouter {

    static ILogger logger = new DefaultLogger(Consts.TAG);
    private static volatile boolean debuggable = false;
    private static volatile boolean hasInit = false;
    private static volatile ThreadPoolExecutor sExecutor = DefaultPoolExecutor.getInstance();
    private static Context sContext;
    private static Handler sHandler;

    private static final class Holder {
        private static final _LiteARouter INSTANCE = new _LiteARouter();
    }

    private _LiteARouter() {
    }

    public static boolean init(Context context) {
        sContext = context.getApplicationContext();
        LogisticsCenter.init(sContext, sExecutor);
        hasInit = true;
        sHandler = new Handler(Looper.getMainLooper());
        logger.info(Consts.TAG, "LiteARouter init success!");
        return true;
    }

    public static void afterInit() {
    }

    public static void destroy() {
        if (isDebuggable()) {
            hasInit = false;
            LogisticsCenter.suspend();
            logger.info(Consts.TAG, "LiteARouter destroy success!");
        } else {
            logger.error(Consts.TAG, "Destroy can be used in debug mode only!");
        }
    }

    protected static _LiteARouter getInstance() {
        if (!hasInit) {
            throw new RuntimeException(Consts.TAG + "Init::Invoke init(context) first!");
        } else {
            return Holder.INSTANCE;
        }
    }

    static synchronized void openDebug() {
        debuggable = true;
        logger.info(Consts.TAG, "LiteARouter openDebug");
    }

    static boolean isDebuggable() {
        return debuggable;
    }

    static synchronized void openLog() {
        logger.showLog(true);
        logger.info(Consts.TAG, "LiteARouter openLog");
    }

    public Postcard build(String path) {
        if (TextUtils.isEmpty(path)) {
            throw new RuntimeException(Consts.TAG + "Parameter is invalid!");
        } else {
            return build(path, extractGroup(path));
        }
    }

    public Postcard build(Uri uri) {
        if (null == uri || TextUtils.isEmpty(uri.toString())) {
            throw new RuntimeException(Consts.TAG + "Parameter invalid!");
        } else {
            return new Postcard(uri.getPath(), extractGroup(uri.getPath()), uri, null);
        }
    }

    public Postcard build(String path, String group) {
        if (TextUtils.isEmpty(path) || TextUtils.isEmpty(group)) {
            throw new RuntimeException(Consts.TAG + "Parameter is invalid!");
        } else {
            return new Postcard(path, group);
        }
    }

    /**
     * Extract the default group from path.
     */
    private String extractGroup(String path) {
        if (TextUtils.isEmpty(path) || !path.startsWith("/")) {
            throw new RuntimeException(Consts.TAG + "Extract the default group failed, the path must be start with '/' and contain more than 2 '/'!");
        }
        try {
            String defaultGroup = path.substring(1, path.indexOf("/", 1));
            if (TextUtils.isEmpty(defaultGroup)) {
                throw new RuntimeException(Consts.TAG + "Extract the default group failed! There's nothing between 2 '/'!");
            } else {
                return defaultGroup;
            }
        } catch (Exception e) {
            logger.warning(Consts.TAG, "Failed to extract default group!");
            e.printStackTrace();
            return null;
        }
    }

    public Object navigation(Context context, final Postcard postcard, int requestCode, NavigationCallback callback) {
        // Set context to postcard.
        postcard.setContext(null == context ? sContext : context);

        try {
            LogisticsCenter.completion(postcard);
        } catch (Exception e) {
            e.printStackTrace();
            if (debuggable) {
                runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(sContext, "There's no route matched!\n" +
                                " Path = [" + postcard.getPath() + "]\n" +
                                " Group = [" + postcard.getGroup() + "]", Toast.LENGTH_LONG).show();
                    }
                });
            }
            if (null != callback) {
                callback.onLost(postcard);
            }
        }
        return _navigation(postcard, requestCode, callback);
    }

    private Object _navigation(final Postcard postcard, final int requestCode, final NavigationCallback callback) {
        final Context currentContext = postcard.getContext();
        switch (postcard.getType()) {
            case ACTIVITY:
                // Build intent
                final Intent intent = new Intent(currentContext, postcard.getDestination());
                intent.putExtras(postcard.getExtras());

                // Set flags
                int flags = postcard.getFlags();
                if (0 != flags) {
                    intent.setFlags(flags);
                }

                // Non activity, need FLAG_ACTIVITY_NEW_TASK
                if (!(currentContext instanceof Activity)) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }

                // Set Actions
                String action = postcard.getAction();
                if (!TextUtils.isEmpty(action)) {
                    intent.setAction(action);
                }

                // Navigation in main looper
                runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(requestCode, currentContext, intent, postcard, callback);
                    }
                });
                break;
            case BOARDCAST:
            case CONTENT_PROVIDER:
            case FRAGMENT:
                Class<?> fragmentMeta = postcard.getDestination();
                try {
                    Object instance = fragmentMeta.getConstructor().newInstance();
                    FragmentCompat.getInstance().setArguments(instance, postcard.getExtras());
                    return instance;
                } catch (Exception e) {
                    logger.error(Consts.TAG, "Fetch fragment instance error");
                    e.printStackTrace();
                }
            case METHOD:
            case SERVICE:
            default:
                return null;
        }
        return null;
    }

    private void startActivity(int requestCode, Context currentContext, Intent intent, Postcard postcard, NavigationCallback callback) {
        if (requestCode >= 0) { // Need start for result
            if (currentContext instanceof Activity) {
                ActivityCompat.getInstance().startActivityForResult((Activity) currentContext, intent, requestCode, postcard.getOptionsBundle());
            } else {
                logger.warning(Consts.TAG, "Must use [navigation(activity, ...)] to support [startActivityForResult]");
            }
        } else {
            ActivityCompat.getInstance().startActivity(currentContext, intent, postcard.getOptionsBundle());
        }

        if ((-1 != postcard.getEnterAnim() && -1 != postcard.getExitAnim()) && currentContext instanceof Activity) { // Old version
            ((Activity) currentContext).overridePendingTransition(postcard.getEnterAnim(), postcard.getExitAnim());
        }

        if (null != callback) { // Navigation over
            callback.onArrival(postcard);
        }
    }

    boolean addRouteGroup(IRouteGroup group) {
        if (null == group) {
            return false;
        }

        String groupName = null;

        try {
            // Extract route meta.
            Map<String, RouteMeta> dynamicRoute = new HashMap<>();
            group.loadInto(dynamicRoute);

            // Check route meta.
            for (Map.Entry<String, RouteMeta> route : dynamicRoute.entrySet()) {
                String path = route.getKey();
                String groupByExtract = extractGroup(path);
                RouteMeta meta = route.getValue();

                if (null == groupName) {
                    groupName = groupByExtract;
                }

                if (null == groupName || !groupName.equals(groupByExtract) || !groupName.equals(meta.getGroup())) {
                    // Group name not consistent
                    return false;
                }
            }

            LogisticsCenter.addRouteGroupDynamic(groupName, group);

            logger.info(Consts.TAG, "Add route group [" + groupName + "] finish, " + dynamicRoute.size() + " new route meta.");

            return true;
        } catch (Exception exception) {
            logger.error(Consts.TAG, "Add route group dynamic exception!", exception);
        }

        return false;
    }

    private void runInMainThread(Runnable runnable) {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            sHandler.post(runnable);
        } else {
            runnable.run();
        }
    }
}

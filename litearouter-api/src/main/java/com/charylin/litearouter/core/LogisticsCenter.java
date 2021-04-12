package com.charylin.litearouter.core;

import android.content.Context;

import com.charylin.litearouter.facade.Postcard;
import com.charylin.litearouter.facade.model.RouteMeta;
import com.charylin.litearouter.facade.template.IRouteGroup;
import com.charylin.litearouter.facade.template.IRouteRoot;
import com.charylin.litearouter.launcher.LiteARouter;
import com.charylin.litearouter.utils.Consts;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.concurrent.ThreadPoolExecutor;

public class LogisticsCenter {

    private static Context sContext;
    private static ThreadPoolExecutor sExecutor;

    private LogisticsCenter() {
    }

    public synchronized static void init(Context context, ThreadPoolExecutor tpe) {
        sContext = context;
        sExecutor = tpe;
        loadRouterMap();
    }

    private static void loadRouterMap() {
        try {
            String nameOfRouteRootClass = Consts.NAME_OF_ROUTE_ROOT_CLASS;
            Class<?> routeRootClz = Class.forName(nameOfRouteRootClass);
            if (routeRootClz != null) {
                IRouteRoot routeGroup = routeRootClz.asSubclass(IRouteRoot.class).newInstance();
                if (routeGroup != null) {
                    routeGroup.loadInto(Warehouse.groupsIndex);
                }
            }
        } catch (Exception e) {
            LiteARouter.logger.error(Consts.TAG, "load router map error.", e);
        }
    }

    /**
     * Suspend business, clear cache.
     */
    public static void suspend() {
        Warehouse.clear();
    }

    /**
     * Completion the postcard by route metas
     *
     * @param postcard Incomplete postcard, should complete by this method.
     */
    public static void completion(Postcard postcard) {
        if (null == postcard) {
            throw new RuntimeException(Consts.TAG + "No Postcard!");
        }

        RouteMeta routeMeta = Warehouse.routes.get(postcard.getPath());
        if (null == routeMeta) {
            // Maybe its does't exist, or didn't load.
            if (!Warehouse.groupsIndex.containsKey(postcard.getGroup())) {
                throw new RuntimeException(Consts.TAG + "There is no route match the path [" + postcard.getPath() + "], in group [" + postcard.getGroup() + "]");
            } else {
                // Load route and cache it into memory, then delete from metas
                try {
                    if (LiteARouter.isDebuggable()) {
                        LiteARouter.logger.debug(Consts.TAG, String.format(Locale.getDefault(), "The group [%s] starts loading, trigger by [%s]", postcard.getGroup(), postcard.getPath()));
                    }

                    addRouteGroupDynamic(postcard.getGroup(), null);

                    if (LiteARouter.isDebuggable()) {
                        LiteARouter.logger.debug(Consts.TAG, String.format(Locale.getDefault(), "The group [%s] has already been loaded, trigger by [%s]", postcard.getGroup(), postcard.getPath()));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(Consts.TAG + "Fatal exception when loading group meta. [" + e.getMessage() + "]");
                }

                completion(postcard); // Reload
            }

        } else {
            postcard.setDestination(routeMeta.getDestination());
            postcard.setType(routeMeta.getType());
            postcard.setPriority(routeMeta.getPriority());
            postcard.setExtra(routeMeta.getExtra());
        }
    }

    public synchronized static void addRouteGroupDynamic(String groupName, IRouteGroup group) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (Warehouse.groupsIndex.containsKey(groupName)) {
            // If this group is included, but it has not been loaded
            // load this group first, because dynamic route has high priority.
            Warehouse.groupsIndex.get(groupName).getConstructor().newInstance().loadInto(Warehouse.routes);
            Warehouse.groupsIndex.remove(groupName);
        }

        // cover old group.
        if (null != group) {
            group.loadInto(Warehouse.routes);
        }
    }
}

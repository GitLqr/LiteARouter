package com.gitlqr.litearouter.core;

import com.gitlqr.litearouter.facade.model.RouteMeta;
import com.gitlqr.litearouter.facade.template.IRouteGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Storage of route meta and other data.
 *
 * @author zhilong <a href="mailto:zhilong.lzl@alibaba-inc.com">Contact me.</a>
 * @version 1.0
 * @since 2017/2/23 下午1:39
 */
class Warehouse {
    // Cache route and metas
    static Map<String, Class<? extends IRouteGroup>> groupsIndex = new HashMap<>();
    static Map<String, RouteMeta> routes = new HashMap<>();

    static void clear() {
        routes.clear();
        groupsIndex.clear();
    }
}

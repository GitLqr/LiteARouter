package com.charylin.litearouter.facade.template;

import com.charylin.litearouter.facade.model.RouteMeta;

import java.util.Map;

/**
 * Group element.
 *
 * @author Alex <a href="mailto:zhilong.liu@aliyun.com">Contact me.</a>
 * @version 1.0
 * @since 16/8/23 16:37
 */
public interface IRouteGroup {
    /**
     * Fill the atlas with routes in group.
     */
    void loadInto(Map<String, RouteMeta> atlas);
}

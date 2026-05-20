package com.cube.material.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 导出表枚举
 * 
 * @author cube
 * @date 2026-05-18
 */
@Getter
@AllArgsConstructor
public enum ExportTableEnum {
    
    ATTRACTION("t_attraction", "景点表", "map"),
    ATTRACTION_PLAN("t_attraction_plan", "景点路线规划表", "map"),
    DISH("t_dish", "美食表", "map"),
    GOODS("t_goods", "商品表", "map"),
    PHONE_POINT("t_phone_point", "打卡点表", "map"),
    PHONE_IMAGE("t_phone_image", "打卡点图片表", "map"),
    POI_BASE("t_poi_base", "POI基础信息表", "map"),
    ROUTE_SEGMENTS("t_route_segments", "路线段表", "map"),
    ROUTES("t_routes", "线路表", "map"),
    VIDEOS("t_videos", "视频表", "map"),
    PRODUCT("t_product", "门票商品表", "product"),
    PRODUCT_TICKET("t_product_ticket", "门票详情表", "product");
    
    /**
     * 表名
     */
    private final String tableName;
    
    /**
     * 表中文名称
     */
    private final String tableDesc;
    
    /**
     * 数据源名称 (map/product)
     */
    private final String datasource;
    
    /**
     * 根据表名获取枚举
     */
    public static ExportTableEnum getByTableName(String tableName) {
        for (ExportTableEnum table : values()) {
            if (table.getTableName().equals(tableName)) {
                return table;
            }
        }
        return null;
    }
}

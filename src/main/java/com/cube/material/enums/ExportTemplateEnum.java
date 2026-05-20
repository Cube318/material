package com.cube.material.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 导出模板枚举（SQL写死在代码中）
 * 
 * @author cube
 * @date 2026-05-18
 */
@Getter
@AllArgsConstructor
public enum ExportTemplateEnum {
    
    /**
     * 二级景点关联视频导出
     */
    ATTRACTION_WITH_VIDEO(
            "attraction_with_video",
            "二级景点关联视频",
            "导出二级景点及其关联的视频信息",
            "map",
            "SELECT a.id as attractionId, a.name, a.title, v.thumbnailUrl, v.videoUrl " +
            "FROM t_attraction a " +
            "LEFT JOIN t_videos v ON v.userId = a.id " +
            "WHERE a.grade = 2",
            new String[]{"attractionId", "name", "title", "thumbnailUrl", "videoUrl"},
            new String[]{"景点ID", "景点名称", "卡片标题", "视频封面", "视频链接"}
    );
    
    /**
     * 热门景点导出（评分>4.5）
     */
    // HOT_ATTRACTIONS(
    //         "hot_attractions",
    //         "热门景点列表",
    //         "导出评分大于4.5的热门景点",
    //         "map",
    //         "SELECT id, name, title, rating, reviewNum, favorite, address " +
    //         "FROM t_attraction " +
    //         "WHERE rating > 4.5 " +
    //         "ORDER BY rating DESC, reviewNum DESC",
    //         new String[]{"id", "name", "title", "rating", "reviewNum", "favorite", "address"},
    //         new String[]{"景点ID", "景点名称", "标题", "评分", "评论数", "收藏数", "地址"}
    // ),
    
    /**
     * POI关联美食
     */
    // POI_WITH_DISH(
    //         "poi_with_dish",
    //         "POI关联美食",
    //         "导出POI及其关联的美食信息",
    //         "map",
    //         "SELECT p.id as poiId, p.name as poiName, d.id as dishId, d.name as dishName, " +
    //         "d.price, d.promoPrice, d.imageUrl " +
    //         "FROM t_poi_base p " +
    //         "LEFT JOIN t_dish d ON d.poiId = p.id " +
    //         "WHERE p.categoryType = 2",
    //         new String[]{"poiId", "poiName", "dishId", "dishName", "price", "promoPrice", "imageUrl"},
    //         new String[]{"POI ID", "POI名称", "美食ID", "美食名称", "价格(分)", "优惠价(分)", "图片URL"}
    // ),
    
    /**
     * 在售门票列表
     */
    // ACTIVE_PRODUCTS(
    //         "active_products",
    //         "在售门票列表",
    //         "导出当前在售的门票商品",
    //         "product",
    //         "SELECT id, name, title, price, stock, sales, isHot, isSwiper, status " +
    //         "FROM t_product " +
    //         "WHERE status = 1 " +
    //         "ORDER BY sales DESC",
    //         new String[]{"id", "name", "title", "price", "stock", "sales", "isHot", "isSwiper", "status"},
    //         new String[]{"商品ID", "商品名称", "标题", "价格", "库存", "销量", "是否热卖", "是否轮播", "状态"}
    // );
    
    /**
     * 模板Key（唯一标识）
     */
    private final String templateKey;
    
    /**
     * 模板名称
     */
    private final String templateName;
    
    /**
     * 模板描述
     */
    private final String templateDesc;
    
    /**
     * 数据源名称
     */
    private final String datasource;
    
    /**
     * SQL查询语句
     */
    private final String sql;
    
    /**
     * 字段列表（英文）
     */
    private final String[] fields;
    
    /**
     * 字段中文名列表
     */
    private final String[] fieldNames;
    
    /**
     * 根据模板Key获取枚举
     */
    public static ExportTemplateEnum getByKey(String templateKey) {
        for (ExportTemplateEnum template : values()) {
            if (template.getTemplateKey().equals(templateKey)) {
                return template;
            }
        }
        return null;
    }
    
    /**
     * 获取字段映射Map
     */
    public Map<String, String> getFieldMap() {
        Map<String, String> fieldMap = new LinkedHashMap<>();
        for (int i = 0; i < fields.length; i++) {
            fieldMap.put(fields[i], fieldNames[i]);
        }
        return fieldMap;
    }
}

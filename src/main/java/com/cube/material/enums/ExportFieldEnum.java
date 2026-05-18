package com.cube.material.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

/**
 * 导出字段枚举 - 字段白名单及中文映射
 * 
 * @author Claude
 * @date 2026-05-18
 */
@Getter
@AllArgsConstructor
public enum ExportFieldEnum {
    
    // ==================== 景点表字段 ====================
    ATTRACTION_ID("id", "景点ID", "t_attraction"),
    ATTRACTION_POI_OBJ_ID("poiObjId", "POI对象ID", "t_attraction"),
    ATTRACTION_NAME("name", "景点名称", "t_attraction"),
    ATTRACTION_TITLE("title", "卡片标题", "t_attraction"),
    ATTRACTION_SCENIC_RATING("scenicRating", "景点评级", "t_attraction"),
    ATTRACTION_GRADE("grade", "景点分级", "t_attraction"),
    ATTRACTION_REC_PRIORITY("recPriority", "推荐优先级", "t_attraction"),
    ATTRACTION_VALUE_DESC("valueDesc", "价值标签", "t_attraction"),
    ATTRACTION_LOCATION_AREA("locationArea", "所在区域", "t_attraction"),
    ATTRACTION_ADVANTAGE("advantage", "景点优势", "t_attraction"),
    ATTRACTION_REVIEW_NUM("reviewNum", "评论量", "t_attraction"),
    ATTRACTION_FAVORITE("favorite", "收藏量", "t_attraction"),
    ATTRACTION_RATING("rating", "评分", "t_attraction"),
    ATTRACTION_DESCRIPTION("description", "景点描述", "t_attraction"),
    ATTRACTION_OPEN_TIME("openTime", "开放时间", "t_attraction"),
    ATTRACTION_CLOSE_TIME("closeTime", "闭园时间", "t_attraction"),
    ATTRACTION_LAST_ENTRY_TIME("lastEntryTime", "最晚入园时间", "t_attraction"),
    ATTRACTION_ADDRESS("address", "地址", "t_attraction"),
    ATTRACTION_IMAGE_URL("imageUrl", "景点图片URL", "t_attraction"),
    ATTRACTION_GUIDE_MAP_URL("guideMapUrl", "导览图URL", "t_attraction"),
    ATTRACTION_GUIDE_MAP_FLAG("guideMapFlag", "有无导览标识", "t_attraction"),
    ATTRACTION_ROUTE_ICON_URL("routeIconUrl", "线路规划图标", "t_attraction"),
    ATTRACTION_SUMMARY_VIDEO_URL("summaryVideoUrl", "短视频URL", "t_attraction"),
    ATTRACTION_TICKET_PRICE("ticketPrice", "景点票价", "t_attraction"),
    ATTRACTION_INTRODUCE("introduce", "景点介绍", "t_attraction"),
    ATTRACTION_INTRODUCE_AUDIO("introduceAudio", "介绍语音URL", "t_attraction"),
    ATTRACTION_EXPLANATION("explanation", "讲解词", "t_attraction"),
    ATTRACTION_EXPLANATION_URL("explanationUrl", "讲解词语音URL", "t_attraction"),
    ATTRACTION_FEATURES_TYPE("featuresType", "景点特征", "t_attraction"),
    ATTRACTION_ATTRACTION_TYPE("attractionType", "景点类型", "t_attraction"),
    ATTRACTION_SCALE("scale", "景点规模", "t_attraction"),
    ATTRACTION_SERVICE_TYPE("serviceType", "服务类型", "t_attraction"),
    ATTRACTION_CREATE_BY("createBy", "创建人", "t_attraction"),
    ATTRACTION_CREATE_TIME("createTime", "创建时间", "t_attraction"),
    ATTRACTION_UPDATE_BY("updateBy", "修改人", "t_attraction"),
    ATTRACTION_UPDATE_TIME("updateTime", "修改时间", "t_attraction"),
    ATTRACTION_INFO("info", "备注", "t_attraction"),
    ATTRACTION_TENANT_ID("tenantId", "租户ID", "t_attraction"),
    
    // ==================== 景点路线规划表字段 ====================
    ATTRACTION_PLAN_ID("id", "主键ID", "t_attraction_plan"),
    ATTRACTION_PLAN_PARENT_ID("parentId", "所属二级景点ID", "t_attraction_plan"),
    ATTRACTION_PLAN_ROUTE_PATH("routePath", "路线路径", "t_attraction_plan"),
    ATTRACTION_PLAN_SEQUENCE("sequence", "路线顺序编号", "t_attraction_plan"),
    ATTRACTION_PLAN_CREATE_BY("createBy", "创建人", "t_attraction_plan"),
    ATTRACTION_PLAN_CREATE_TIME("createTime", "创建时间", "t_attraction_plan"),
    ATTRACTION_PLAN_UPDATE_BY("updateBy", "修改人", "t_attraction_plan"),
    ATTRACTION_PLAN_UPDATE_TIME("updateTime", "修改时间", "t_attraction_plan"),
    ATTRACTION_PLAN_INFO("info", "备注", "t_attraction_plan"),
    ATTRACTION_PLAN_TENANT_ID("tenantId", "租户ID", "t_attraction_plan"),
    
    // ==================== 美食表字段 ====================
    DISH_ID("id", "主键ID", "t_dish"),
    DISH_POI_ID("poiId", "POI基础表ID", "t_dish"),
    DISH_NAME("name", "菜品名称", "t_dish"),
    DISH_TITLE("title", "副标题", "t_dish"),
    DISH_PRICE("price", "菜品价格(分)", "t_dish"),
    DISH_VALUE_DESC("valueDesc", "价值标签", "t_dish"),
    DISH_PROMO_PRICE("promoPrice", "优惠价格(分)", "t_dish"),
    DISH_IMAGE_URL("imageUrl", "菜品图片URL", "t_dish"),
    DISH_THEMEBG("themebg", "优惠主题色", "t_dish"),
    DISH_COVER_IMAGE_URL("coverImageUrl", "起播图", "t_dish"),
    DISH_VIDEO_URL("videoUrl", "视频链接", "t_dish"),
    DISH_ORDER_SORT("orderSort", "排序", "t_dish"),
    DISH_LINK("link", "优惠链接", "t_dish"),
    DISH_DESCRIPTION("description", "菜品描述", "t_dish"),
    DISH_CREATE_BY("createBy", "创建人", "t_dish"),
    DISH_CREATE_TIME("createTime", "创建时间", "t_dish"),
    DISH_UPDATE_BY("updateBy", "修改人", "t_dish"),
    DISH_UPDATE_TIME("updateTime", "修改时间", "t_dish"),
    DISH_INFO("info", "备注", "t_dish"),
    DISH_TENANT_ID("tenantId", "租户ID", "t_dish"),
    
    // ==================== 商品表字段 ====================
    GOODS_ID("id", "主键ID", "t_goods"),
    GOODS_POI_ID("poiId", "POI基础表ID", "t_goods"),
    GOODS_NAME("name", "商品名称", "t_goods"),
    GOODS_TITLE("title", "副标题", "t_goods"),
    GOODS_XD_PRODUCT_ID("xdProductId", "小店商品ID", "t_goods"),
    GOODS_PRICE("price", "商品价格(分)", "t_goods"),
    GOODS_GOODS_TYPE("goodsType", "商品类型", "t_goods"),
    GOODS_VALUE_DESC("valueDesc", "价值标签", "t_goods"),
    GOODS_PROMO_PRICE("promoPrice", "优惠价格(分)", "t_goods"),
    GOODS_IMAGE_URL("imageUrl", "商品图片URL", "t_goods"),
    GOODS_THEMEBG("themebg", "优惠主题色", "t_goods"),
    GOODS_COVER_IMAGE_URL("coverImageUrl", "起播图", "t_goods"),
    GOODS_VIDEO_URL("videoUrl", "视频链接", "t_goods"),
    GOODS_ORDER_SORT("orderSort", "排序", "t_goods"),
    GOODS_LINK("link", "优惠链接", "t_goods"),
    GOODS_DESCRIPTION("description", "商品描述", "t_goods"),
    GOODS_CREATE_BY("createBy", "创建人", "t_goods"),
    GOODS_CREATE_TIME("createTime", "创建时间", "t_goods"),
    GOODS_UPDATE_BY("updateBy", "修改人", "t_goods"),
    GOODS_UPDATE_TIME("updateTime", "修改时间", "t_goods"),
    GOODS_INFO("info", "备注", "t_goods"),
    GOODS_TENANT_ID("tenantId", "租户ID", "t_goods"),
    GOODS_IS_ADVERTISING("isAdvertising", "是否广告", "t_goods"),
    
    // ==================== 打卡点表字段 ====================
    PHONE_POINT_ID("id", "主键ID", "t_phone_point"),
    PHONE_POINT_TITLE("title", "网红打卡主题", "t_phone_point"),
    PHONE_POINT_ATTRACTION_ID("attractionId", "机位景点ID", "t_phone_point"),
    PHONE_POINT_ATTRACTION_NAME("attractionName", "机位景点名称", "t_phone_point"),
    PHONE_POINT_PHOTO_POINT("photoPoint", "具体机位", "t_phone_point"),
    PHONE_POINT_PHOTO_POINT_GPS("photoPointGPS", "机位GPS", "t_phone_point"),
    PHONE_POINT_PHOTO_TIME("PhotoTime", "最佳拍摄时间", "t_phone_point"),
    PHONE_POINT_RATING("rating", "评分", "t_phone_point"),
    PHONE_POINT_PREFIX_TITLE_ICON("prefixTitleIcon", "主题前图标", "t_phone_point"),
    PHONE_POINT_SUFFIX_TITLE_ICON("SuffixTitleIcon", "主题后图标", "t_phone_point"),
    PHONE_POINT_PHOTO_POINT_ICON("photoPointIcon", "打卡机位前图标", "t_phone_point"),
    PHONE_POINT_OPEN_TIME("openTime", "开放时间", "t_phone_point"),
    PHONE_POINT_CLOSE_TIME("closeTime", "闭园时间", "t_phone_point"),
    PHONE_POINT_LAST_ENTRY_TIME("lastEntryTime", "最晚入园时间", "t_phone_point"),
    PHONE_POINT_VIDEO_URL("videoUrl", "打卡点视频", "t_phone_point"),
    PHONE_POINT_IMAGE_URL("imageUrl", "打卡点封面", "t_phone_point"),
    PHONE_POINT_CREATE_BY("createBy", "创建人", "t_phone_point"),
    PHONE_POINT_CREATE_TIME("createTime", "创建时间", "t_phone_point"),
    PHONE_POINT_UPDATE_BY("updateBy", "修改人", "t_phone_point"),
    PHONE_POINT_UPDATE_TIME("updateTime", "修改时间", "t_phone_point"),
    PHONE_POINT_INFO("info", "备注", "t_phone_point"),
    PHONE_POINT_TENANT_ID("tenantId", "租户ID", "t_phone_point"),
    
    // ==================== 打卡点图片表字段 ====================
    PHONE_IMAGE_ID("id", "主键ID", "t_phone_image"),
    PHONE_IMAGE_PHONE_POINT_ID("phonePointId", "打卡点ID", "t_phone_image"),
    PHONE_IMAGE_PHONE_POINT_URL("phonePointUrl", "照片URL", "t_phone_image"),
    PHONE_IMAGE_CREATE_BY("createBy", "创建人", "t_phone_image"),
    PHONE_IMAGE_CREATE_TIME("createTime", "创建时间", "t_phone_image"),
    PHONE_IMAGE_UPDATE_BY("updateBy", "修改人", "t_phone_image"),
    PHONE_IMAGE_UPDATE_TIME("updateTime", "修改时间", "t_phone_image"),
    PHONE_IMAGE_INFO("info", "备注", "t_phone_image"),
    PHONE_IMAGE_IS_VERTICAL("isVertical", "是否竖屏", "t_phone_image"),
    PHONE_IMAGE_TENANT_ID("tenantId", "租户ID", "t_phone_image"),
    
    // ==================== POI基础信息表字段 ====================
    POI_BASE_ID("id", "主键ID", "t_poi_base"),
    POI_BASE_OBJECT_ID("objectId", "高德地图ID", "t_poi_base"),
    POI_BASE_AOI_OBJ_ID("aoiObjId", "AOI的ObjectId", "t_poi_base"),
    POI_BASE_NAME("name", "POI名称", "t_poi_base"),
    POI_BASE_ADDRESS("address", "详细地址", "t_poi_base"),
    POI_BASE_REVIEW_NUM("reviewNum", "评论量", "t_poi_base"),
    POI_BASE_FAVORITE("favorite", "收藏量", "t_poi_base"),
    POI_BASE_RATING("rating", "综合评分", "t_poi_base"),
    POI_BASE_LONGITUDE("longitude", "经度", "t_poi_base"),
    POI_BASE_LATITUDE("latitude", "纬度", "t_poi_base"),
    POI_BASE_TEL("tel", "联系电话", "t_poi_base"),
    POI_BASE_BUSINESS_HOURS("businessHours", "营业时间", "t_poi_base"),
    POI_BASE_CATEGORY_TYPE("categoryType", "类型", "t_poi_base"),
    POI_BASE_POI_TYPE("poiType", "POI类型", "t_poi_base"),
    POI_BASE_DESCRIPTION("description", "详细描述", "t_poi_base"),
    POI_BASE_STATUS("status", "状态", "t_poi_base"),
    POI_BASE_CREATE_BY("createBy", "创建人", "t_poi_base"),
    POI_BASE_CREATE_TIME("createTime", "创建时间", "t_poi_base"),
    POI_BASE_UPDATE_BY("updateBy", "修改人", "t_poi_base"),
    POI_BASE_UPDATE_TIME("updateTime", "修改时间", "t_poi_base"),
    POI_BASE_INFO("info", "备注", "t_poi_base"),
    
    // ==================== 路线段表字段 ====================
    ROUTE_SEGMENTS_ID("id", "主键ID", "t_route_segments"),
    ROUTE_SEGMENTS_ROUTE_ID("routeId", "线路ID", "t_route_segments"),
    ROUTE_SEGMENTS_ATTRACTION_ID("attractionId", "景点ID", "t_route_segments"),
    ROUTE_SEGMENTS_ATTRACTION_NAME("attractionName", "景点名称", "t_route_segments"),
    ROUTE_SEGMENTS_END_ATTRACTION_ID("endAttractionId", "目的地ID", "t_route_segments"),
    ROUTE_SEGMENTS_END_ATTRACTION_NAME("endAttractionName", "目的地名称", "t_route_segments"),
    ROUTE_SEGMENTS_MODE_OF_TRANSPORT_ID("modeofTransportId", "交通方式ID", "t_route_segments"),
    ROUTE_SEGMENTS_DURATION("duration", "线路时长", "t_route_segments"),
    ROUTE_SEGMENTS_DISTANCE("distance", "距离", "t_route_segments"),
    ROUTE_SEGMENTS_SEGMENT_ORDER("segmentOrder", "路线段顺序", "t_route_segments"),
    ROUTE_SEGMENTS_CLIENTX("clientx", "左边距距离", "t_route_segments"),
    ROUTE_SEGMENTS_CLIENTY("clienty", "上边距距离", "t_route_segments"),
    ROUTE_SEGMENTS_AGENT_ID("agentId", "智能体ID", "t_route_segments"),
    ROUTE_SEGMENTS_CREATE_BY("createBy", "创建人", "t_route_segments"),
    ROUTE_SEGMENTS_CREATE_TIME("createTime", "创建时间", "t_route_segments"),
    ROUTE_SEGMENTS_UPDATE_BY("updateBy", "修改人", "t_route_segments"),
    ROUTE_SEGMENTS_UPDATE_TIME("updateTime", "修改时间", "t_route_segments"),
    ROUTE_SEGMENTS_INFO("info", "备注", "t_route_segments"),
    ROUTE_SEGMENTS_TENANT_ID("tenantId", "租户ID", "t_route_segments"),
    
    // ==================== 线路表字段 ====================
    ROUTES_ID("id", "主键ID", "t_routes"),
    ROUTES_NAME("name", "线路名称", "t_routes"),
    ROUTES_SIGHT_ID("sightId", "景区ID", "t_routes"),
    ROUTES_ROUTES_DESC("routesDesc", "路线特色说明", "t_routes"),
    ROUTES_TAGS("tags", "标签", "t_routes"),
    ROUTES_WALK_DURATION("walkDuration", "步行时间", "t_routes"),
    ROUTES_CYCLING_DURATION("cyclingDuration", "骑行时间", "t_routes"),
    ROUTES_TAXI_DURATION("taxiDuration", "打车时间", "t_routes"),
    ROUTES_FERRY_DURATION("ferryDuration", "轮渡时间", "t_routes"),
    ROUTES_ROUTE_DISTANCE("routeDistance", "路线距离", "t_routes"),
    ROUTES_COVER_IMAGE_URL("coverImageUrl", "路线视频起播图", "t_routes"),
    ROUTES_AGENT_ID("agentId", "智能体ID", "t_routes"),
    ROUTES_CREATE_BY("createBy", "创建人", "t_routes"),
    ROUTES_CREATE_TIME("createTime", "创建时间", "t_routes"),
    ROUTES_UPDATE_BY("updateBy", "修改人", "t_routes"),
    ROUTES_UPDATE_TIME("updateTime", "修改时间", "t_routes"),
    ROUTES_INFO("info", "备注", "t_routes"),
    ROUTES_X_SCALE("xScale", "X缩放比", "t_routes"),
    ROUTES_Y_SCALE("yScale", "Y缩放比", "t_routes"),
    ROUTES_Q_SCALE("qScale", "调整因子", "t_routes"),
    ROUTES_RATING("rating", "路线评分", "t_routes"),
    ROUTES_VIDEO_URL("videoUrl", "路线视频", "t_routes"),
    ROUTES_TENANT_ID("tenantId", "租户ID", "t_routes"),
    
    // ==================== 视频表字段 ====================
    VIDEOS_ID("id", "主键ID", "t_videos"),
    VIDEOS_USER_ID("userId", "达人ID", "t_videos"),
    VIDEOS_TITLE("title", "视频标题", "t_videos"),
    VIDEOS_DESCRIPTION("description", "视频描述", "t_videos"),
    VIDEOS_ORIENTATION_TYPE("orientationType", "方向类型", "t_videos"),
    VIDEOS_THUMBNAIL_URL("thumbnailUrl", "视频起播图", "t_videos"),
    VIDEOS_THEME_COLOR("themeColor", "主题色", "t_videos"),
    VIDEOS_VIDEO_URL("videoUrl", "视频链接", "t_videos"),
    VIDEOS_CREATE_DATE("createDate", "视频发布时间", "t_videos"),
    VIDEOS_UPDATE_DATE("updateDate", "视频修改时间", "t_videos"),
    VIDEOS_FLAG("flag", "审核状态", "t_videos"),
    VIDEOS_CREATE_BY("createBy", "创建人", "t_videos"),
    VIDEOS_CREATE_TIME("createTime", "创建时间", "t_videos"),
    VIDEOS_UPDATE_BY("updateBy", "修改人", "t_videos"),
    VIDEOS_UPDATE_TIME("updateTime", "修改时间", "t_videos"),
    VIDEOS_INFO("info", "备注", "t_videos"),
    VIDEOS_TENANT_ID("tenantId", "租户ID", "t_videos"),
    
    // ==================== 门票商品表字段 ====================
    PRODUCT_ID("id", "主键ID", "t_product"),
    PRODUCT_EXPERT_ID("expertId", "店长ID", "t_product"),
    PRODUCT_NAME("name", "产品名称", "t_product"),
    PRODUCT_TITLE("title", "产品小图名称", "t_product"),
    PRODUCT_THEME_COLOR("themeColor", "主题色", "t_product"),
    PRODUCT_PRICE("price", "价格", "t_product"),
    PRODUCT_STOCK("stock", "库存", "t_product"),
    PRODUCT_SALES("sales", "销量", "t_product"),
    PRODUCT_PRO_PIC("proPic", "产品图片", "t_product"),
    PRODUCT_PRO_ICON("proIcon", "商品图标", "t_product"),
    PRODUCT_RECOMMEND_PREFS("recommendPrefs", "推荐偏好", "t_product"),
    PRODUCT_IS_HOT("isHot", "是否热卖", "t_product"),
    PRODUCT_IS_SWIPER("isSwiper", "是否首页轮播", "t_product"),
    PRODUCT_SWIPER_PIC("swiperPic", "轮播图片地址", "t_product"),
    PRODUCT_SWIPER_SORT("swiperSort", "轮播顺序", "t_product"),
    PRODUCT_TYPE_ID("typeId", "商品类别", "t_product"),
    PRODUCT_HOT_DATE_TIME("hotDateTime", "设置热卖时间", "t_product"),
    PRODUCT_PRODUCT_INTRO_IMGS("productIntroImgs", "产品介绍图片", "t_product"),
    PRODUCT_PRODUCT_PARA_IMGS("productParaImgs", "产品参数图片", "t_product"),
    PRODUCT_DESCRIPTION("description", "产品描述", "t_product"),
    PRODUCT_CREATE_BY("createBy", "创建人", "t_product"),
    PRODUCT_CREATE_TIME("createTime", "创建时间", "t_product"),
    PRODUCT_UPDATE_BY("updateBy", "修改人", "t_product"),
    PRODUCT_UPDATE_TIME("updateTime", "修改时间", "t_product"),
    PRODUCT_INFO("info", "备注", "t_product"),
    PRODUCT_STATUS("status", "状态", "t_product"),
    PRODUCT_ADDRESS("address", "门票商品地址", "t_product"),
    PRODUCT_TAG("tag", "标签", "t_product"),
    PRODUCT_LINK("link", "购买链接", "t_product"),
    PRODUCT_GOODS_ID("goodsId", "商品ID", "t_product"),
    PRODUCT_FS_TYPE("fsType", "FS类型", "t_product"),
    PRODUCT_REAL_NAME_TYPE("realNameType", "实名类型", "t_product"),
    PRODUCT_OPEN_TIME("openTime", "开放时间", "t_product"),
    PRODUCT_CLOSE_TIME("closeTime", "关闭时间", "t_product"),
    PRODUCT_LAST_ENTRY_TIME("lastEntryTime", "最晚入园时间", "t_product"),
    PRODUCT_PRO_HEADER_VIDEO("proHeaderVideo", "横屏视频", "t_product"),
    PRODUCT_PRO_HEADER_IMAGE("proHeaderImage", "横屏起播图", "t_product"),
    PRODUCT_NOTICE("notice", "须知", "t_product"),
    PRODUCT_BOOKING_NOTICE("bookingNotice", "预订须知", "t_product"),
    PRODUCT_TENANT_ID("tenantId", "租户ID", "t_product"),
    PRODUCT_IS_HALF_SCREEN("isHalfScreen", "是否拉起小程序半屏", "t_product"),
    PRODUCT_HALF_SCREEN_URL("halfScreenUrl", "小程序半屏跳转地址", "t_product"),
    PRODUCT_APP_ID("appId", "小程序appid", "t_product"),
    PRODUCT_TICKET_NOTICE("ticketNotice", "门票首页展示预定须知", "t_product"),
    
    // ==================== 门票详情表字段 ====================
    PRODUCT_TICKET_ID("id", "主键ID", "t_product_ticket"),
    PRODUCT_TICKET_PRODUCT_ID("productId", "产品ID", "t_product_ticket"),
    PRODUCT_TICKET_GOODS_CODE("goodsCode", "商品编码", "t_product_ticket"),
    PRODUCT_TICKET_GOODS_NAME("goodsName", "商品名称", "t_product_ticket"),
    PRODUCT_TICKET_REAL_NAME_TYPE("realNameType", "实名类型", "t_product_ticket"),
    PRODUCT_TICKET_FS_TYPE("fsType", "FS类型", "t_product_ticket"),
    PRODUCT_TICKET_GOODS_PRICE("goodsPrice", "商品价格(分)", "t_product_ticket"),
    PRODUCT_TICKET_CREATE_BY("createBy", "创建人", "t_product_ticket"),
    PRODUCT_TICKET_CREATE_TIME("createTime", "创建时间", "t_product_ticket"),
    PRODUCT_TICKET_UPDATE_BY("updateBy", "修改人", "t_product_ticket"),
    PRODUCT_TICKET_UPDATE_TIME("updateTime", "修改时间", "t_product_ticket"),
    PRODUCT_TICKET_INFO("info", "备注", "t_product_ticket"),
    PRODUCT_TICKET_TICKET_NOTICE("ticketNotice", "须知", "t_product_ticket"),
    PRODUCT_TICKET_GOODS_TYPE("goodsType", "商品类型", "t_product_ticket"),
    PRODUCT_TICKET_IS_INPUT_BUYER_CARD_NO("isInputBuyerCardNo", "是否输入购买者身份证", "t_product_ticket"),
    PRODUCT_TICKET_GOODS_ALIAS_NAME("goodsAliasName", "商品别名", "t_product_ticket"),
    PRODUCT_TICKET_TAGS("tags", "标签", "t_product_ticket"),
    PRODUCT_TICKET_TYPE_ID("typeId", "商品类别", "t_product_ticket"),
    PRODUCT_TICKET_ORIGINAL_PRICE("originalPrice", "原价", "t_product_ticket"),
    PRODUCT_TICKET_TENANT_ID("tenantId", "租户ID", "t_product_ticket");
    
    /**
     * 字段英文名（数据库字段）
     */
    private final String fieldKey;
    
    /**
     * 字段中文名（Excel列名）
     */
    private final String fieldName;
    
    /**
     * 所属表名
     */
    private final String tableName;
    
    /**
     * 获取指定表的所有字段映射
     */
    public static Map<String, String> getFieldMapByTable(String tableName) {
        Map<String, String> fieldMap = new LinkedHashMap<>();
        for (ExportFieldEnum field : values()) {
            if (field.getTableName().equals(tableName)) {
                fieldMap.put(field.getFieldKey(), field.getFieldName());
            }
        }
        return fieldMap;
    }
    
    /**
     * 获取指定表的字段白名单
     */
    public static Set<String> getWhitelistByTable(String tableName) {
        Set<String> whitelist = new LinkedHashSet<>();
        for (ExportFieldEnum field : values()) {
            if (field.getTableName().equals(tableName)) {
                whitelist.add(field.getFieldKey());
            }
        }
        return whitelist;
    }
    
    /**
     * 校验字段是否在白名单中
     */
    public static boolean isValidField(String tableName, String fieldKey) {
        return getWhitelistByTable(tableName).contains(fieldKey);
    }
    
    /**
     * 根据表名和字段key获取中文名
     */
    public static String getFieldName(String tableName, String fieldKey) {
        for (ExportFieldEnum field : values()) {
            if (field.getTableName().equals(tableName) && field.getFieldKey().equals(fieldKey)) {
                return field.getFieldName();
            }
        }
        return fieldKey;
    }
}

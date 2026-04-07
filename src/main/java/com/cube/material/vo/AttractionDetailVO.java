package com.cube.material.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author cube
 */
@Data
public class AttractionDetailVO {

    // ==================== t_attraction 表字段 ====================
    private Long id;
    private String poiObjId;
    private String name;
    private String title;
    private String scenicRating;
    private Integer grade;
    private Integer recPriority;
    private String valueDesc;
    private String locationArea;
    private String advantage;
    private Integer reviewNum;
    private Integer favorite;
    private Double rating;
    private String description;
    private String openTime;
    private String closeTime;
    private String lastEntryTime;
    private String address;
    private String imageUrl;
    private String guideMapUrl;
    private Integer guideMapFlag;
    private String routeIconUrl;
    private String summaryVideoUrl;
    private String ticketPrice;
    private String introduce;
    private String introduceAudio;
    private String explanation;
    private String explanationUrl;
    private String featuresType;
    private String attractionType;
    private String scale;
    private String serviceType;
    private String createBy;
    private LocalDateTime createTime;
    private String updateBy;
    private LocalDateTime updateTime;
    private String info;

    // ==================== POI 信息（嵌套对象） ====================
    private PoiInfoVO poi;

    // ==================== 新增：该景点关联的视频列表 ====================
    private List<VideoVO> videos;

    @Data
    public static class PoiInfoVO {
        private String objectId;
        private String name;
        private String address;
        private Double longitude;
        private Double latitude;
        private String tel;
        private String businessHours;
        private Integer categoryType;
        private Integer poiType;
        private String description;
        private Integer status;
        private Double rating;
        private Integer reviewNum;
        private Integer favorite;
    }

    // ==================== 视频 VO（推荐单独定义，便于维护） ====================
    @Data
    public static class VideoVO {
        private Integer id;
        private Integer userId;
        private String title;
        private String description;
        private Integer orientationType;
        private String thumbnailUrl;
        private String themeColor;
        private String videoUrl;
        private LocalDateTime createDate;
        private LocalDateTime updateDate;
        private String flag;
        private String createBy;
        private LocalDateTime createTime;
        private String updateBy;
        private LocalDateTime updateTime;
        private String info;

        // 如果你只想返回核心字段，也可以只保留下面这些，删除上面多余的
        // private Integer id;
        // private String title;
        // private String description;
        // private Integer orientationType;
        // private String thumbnailUrl;
        // private String themeColor;
        // private String videoUrl;
        // private LocalDateTime createDate;
        // private String flag;
    }
}
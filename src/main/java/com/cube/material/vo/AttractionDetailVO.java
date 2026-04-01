package com.cube.material.vo;

import lombok.Data;
import java.time.LocalDateTime;

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

    // ==================== POI 信息（嵌套对象，更清晰） ====================
    private PoiInfoVO poi;

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
}
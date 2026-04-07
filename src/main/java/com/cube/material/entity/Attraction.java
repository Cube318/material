package com.cube.material.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author cube
 */
@TableName("t_attraction")
@Data
public class Attraction {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("poiObjId")
    private String poiObjId;

    private String name;

    @TableField("title")
    private String title;

    @TableField("scenicRating")
    private String scenicRating;

    @TableField("grade")
    private Integer grade;

    @TableField("recPriority")
    private Integer recPriority;

    @TableField("valueDesc")
    private String valueDesc;

    @TableField("locationArea")
    private String locationArea;

    @TableField("advantage")
    private String advantage;

    @TableField("reviewNum")
    private Integer reviewNum;

    @TableField("favorite")
    private Integer favorite;

    @TableField("rating")
    private Double rating;

    private String description;

    @TableField("openTime")
    private String openTime;

    @TableField("closeTime")
    private String closeTime;

    @TableField("lastEntryTime")
    private String lastEntryTime;

    private String address;

    @TableField("imageUrl")
    private String imageUrl;

    @TableField("guideMapUrl")
    private String guideMapUrl;

    @TableField("guideMapFlag")
    private Integer guideMapFlag;

    @TableField("routeIconUrl")
    private String routeIconUrl;

    @TableField("summaryVideoUrl")
    private String summaryVideoUrl;

    @TableField("ticketPrice")
    private String ticketPrice;

    private String introduce;

    @TableField("introduceAudio")
    private String introduceAudio;

    private String explanation;

    @TableField("explanationUrl")
    private String explanationUrl;

    @TableField("featuresType")
    private String featuresType;

    @TableField("attractionType")
    private String attractionType;

    @TableField("scale")
    private String scale;

    @TableField("serviceType")
    private String serviceType;

    @TableField("createBy")
    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("createTime")
    private LocalDateTime createTime;

    @TableField("updateBy")
    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("updateTime")
    private LocalDateTime updateTime;

    private String info;
}
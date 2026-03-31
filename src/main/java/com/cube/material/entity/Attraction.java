package com.cube.material.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_attraction")
public class Attraction {

    @TableId(type = IdType.AUTO)
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
    private Date createTime;
    private String updateBy;
    private Date updateTime;
    private String info;
    private String tenantId;
}
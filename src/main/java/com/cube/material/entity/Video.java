package com.cube.material.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 视频实体类
 * 对应数据库表：t_videos
 * @author cube
 */
@Data
@TableName("t_videos")
public class Video {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("userId")
    private Integer userId;

    @TableField("title")
    private String title;

    @TableField("description")
    private String description;

    @TableField("orientationType")
    private Integer orientationType;

    @TableField("thumbnailUrl")
    private String thumbnailUrl;

    @TableField("themeColor")
    private String themeColor;

    @TableField("videoUrl")
    private String videoUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("createDate")
    private LocalDateTime createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("updateDate")
    private LocalDateTime updateDate;

    @TableField("flag")
    private String flag;

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

    @TableField("info")
    private String info;

    @TableField("tenantId")
    private String tenantId;
}

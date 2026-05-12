package com.cube.material.vo;

import lombok.Data;

/**
 * 二级景点与视频关联 VO
 */
@Data
public class AttractionVideoVO {

    private Long attractionId;
    private String name;
    private String title;

    private Integer videoId;
    private String videoTitle;
    private String thumbnailUrl;
    private String videoUrl;
}

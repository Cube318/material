package com.cube.material.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 视频上传返回对象
 * @author cube
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoUploadVO {

    /**
     * 视频文件的完整URL
     */
    private String fileUrl;

    /**
     * 存储的文件名
     */
    private String fileName;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 视频时长（秒）
     */
    private Integer duration;

    /**
     * 视频分辨率
     */
    private String resolution;
}

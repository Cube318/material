package com.cube.material.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 起播图上传返回对象
 * @author cube
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThumbnailUploadVO {

    /**
     * 图片文件的完整URL
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
     * 图片宽度
     */
    private Integer width;

    /**
     * 图片高度
     */
    private Integer height;
}

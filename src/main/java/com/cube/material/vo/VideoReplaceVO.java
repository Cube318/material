package com.cube.material.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 视频替换完整流程返回对象
 * @author cube
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoReplaceVO {

    /**
     * 新视频的完整URL
     */
    private String videoUrl;

    /**
     * 新起播图的完整URL
     */
    private String thumbnailUrl;

    /**
     * 新视频的ID
     */
    private String videoId;

    /**
     * 视频详细信息
     */
    private VideoInfo videoInfo;

    /**
     * 起播图详细信息
     */
    private ThumbnailInfo thumbnailInfo;

    /**
     * 处理步骤记录
     */
    private List<ProcessStep> processSteps;

    /**
     * 视频详细信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VideoInfo {
        /**
         * 视频时长（秒）
         */
        private Integer duration;

        /**
         * 视频分辨率
         */
        private String resolution;

        /**
         * 文件大小（字节）
         */
        private Long fileSize;

        /**
         * 视频格式
         */
        private String format;
    }

    /**
     * 起播图详细信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThumbnailInfo {
        /**
         * 图片宽度
         */
        private Integer width;

        /**
         * 图片高度
         */
        private Integer height;

        /**
         * 文件大小（字节）
         */
        private Long fileSize;
    }

    /**
     * 处理步骤
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProcessStep {
        /**
         * 步骤名称：upload / transcode / thumbnail / database
         */
        private String step;

        /**
         * 状态：success / failed / processing
         */
        private String status;

        /**
         * 消息
         */
        private String message;

        /**
         * 时间戳
         */
        private String timestamp;
    }
}

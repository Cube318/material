package com.cube.material.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 视频替换完整流程请求对象
 * @author cube
 */
@Data
public class VideoReplaceReq {

    /**
     * 视频文件对象
     */
    @NotNull(message = "视频文件不能为空")
    private MultipartFile videoFile;

    /**
     * 起播图文件（可选）
     */
    private MultipartFile thumbnailFile;

    /**
     * 卡片类型：attraction / food / product
     */
    @NotBlank(message = "卡片类型不能为空")
    private String cardType;

    /**
     * 内容ID（景点ID/食品ID/商品ID）
     */
    @NotBlank(message = "内容ID不能为空")
    private String contentId;

    /**
     * 视频文件名（前端生成）
     */
    @NotBlank(message = "视频文件名不能为空")
    private String videoFileName;

    /**
     * 起播图文件名（可选）
     */
    private String thumbnailFileName;

    /**
     * 起播图来源：upload / capture / auto
     */
    private String thumbnailSource;

    /**
     * 是否自动生成起播图
     */
    private Boolean autoGenerateThumbnail;
}

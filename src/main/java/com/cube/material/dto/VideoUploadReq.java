package com.cube.material.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 视频上传请求对象
 * @author cube
 */
@Data
public class VideoUploadReq {

    /**
     * 视频文件对象
     */
    @NotNull(message = "视频文件不能为空")
    private MultipartFile file;

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
     * 新文件名（前端生成）
     */
    @NotBlank(message = "文件名不能为空")
    private String fileName;
}

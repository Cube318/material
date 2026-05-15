package com.cube.material.controller;

import com.cube.material.common.RetInfo;
import com.cube.material.service.VideoService;
import com.cube.material.vo.ThumbnailUploadVO;
import com.cube.material.vo.VideoReplaceVO;
import com.cube.material.vo.VideoUploadVO;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 视频控制器
 * 提供视频上传、起播图上传、视频替换等功能
 * @author cube
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/video")
public class VideoController {

    @Resource
    private VideoService videoService;

    /**
     * 上传视频文件
     * 
     * @param file 视频文件对象
     * @param cardType 卡片类型（attraction / food / product）
     * @param contentId 内容ID（景点ID/食品ID/商品ID）
     * @param fileName 新文件名（前端生成）
     * @return 视频上传结果
     */
    @PostMapping("/upload")
    public RetInfo<VideoUploadVO> uploadVideo(
            @RequestParam("file") @NotNull(message = "视频文件不能为空") MultipartFile file,
            @RequestParam("cardType") @NotBlank(message = "卡片类型不能为空") String cardType,
            @RequestParam("contentId") @NotBlank(message = "内容ID不能为空") String contentId,
            @RequestParam("fileName") @NotBlank(message = "文件名不能为空") String fileName) {
        
        log.info("========== 接收视频上传请求：cardType={}, contentId={}, fileName={}, fileSize={} ==========",
                cardType, contentId, fileName, file.getSize());

        try {
            VideoUploadVO result = videoService.uploadVideo(file, cardType, contentId, fileName);
            return RetInfo.ok(result);
        } catch (IllegalArgumentException e) {
            log.error("参数错误：{}", e.getMessage());
            return RetInfo.error(400, e.getMessage());
        } catch (Exception e) {
            log.error("视频上传失败：{}", e.getMessage(), e);
            return RetInfo.error(500, "视频上传失败：" + e.getMessage());
        }
    }

    /**
     * 上传起播图文件
     * 
     * @param file 图片文件对象
     * @param cardType 卡片类型
     * @param contentId 内容ID
     * @param fileName 新文件名（前端生成）
     * @param source 起播图来源（upload / capture）
     * @return 起播图上传结果
     */
    @PostMapping("/uploadThumbnail")
    public RetInfo<ThumbnailUploadVO> uploadThumbnail(
            @RequestParam("file") @NotNull(message = "图片文件不能为空") MultipartFile file,
            @RequestParam("cardType") @NotBlank(message = "卡片类型不能为空") String cardType,
            @RequestParam("contentId") @NotBlank(message = "内容ID不能为空") String contentId,
            @RequestParam("fileName") @NotBlank(message = "文件名不能为空") String fileName,
            @RequestParam("source") @NotBlank(message = "起播图来源不能为空") String source) {
        
        log.info("========== 接收起播图上传请求：cardType={}, contentId={}, fileName={}, source={}, fileSize={} ==========",
                cardType, contentId, fileName, source, file.getSize());

        try {
            ThumbnailUploadVO result = videoService.uploadThumbnail(file, cardType, contentId, fileName, source);
            return RetInfo.ok(result);
        } catch (IllegalArgumentException e) {
            log.error("参数错误：{}", e.getMessage());
            return RetInfo.error(400, e.getMessage());
        } catch (Exception e) {
            log.error("起播图上传失败：{}", e.getMessage(), e);
            return RetInfo.error(500, "起播图上传失败：" + e.getMessage());
        }
    }

    /**
     * 替换视频（完整流程）
     * 一次性完成视频替换的完整流程（推荐使用此接口）
     * 
     * @param videoFile 视频文件对象
     * @param thumbnailFile 起播图文件（可选）
     * @param cardType 卡片类型
     * @param contentId 内容ID
     * @param videoFileName 视频文件名
     * @param thumbnailFileName 起播图文件名
     * @param thumbnailSource 起播图来源（upload / capture / auto）
     * @param autoGenerateThumbnail 是否自动生成起播图
     * @return 视频替换结果
     */
    @PostMapping("/replace")
    public RetInfo<VideoReplaceVO> replaceVideo(
            @RequestParam("videoFile") @NotNull(message = "视频文件不能为空") MultipartFile videoFile,
            @RequestParam(value = "thumbnailFile", required = false) MultipartFile thumbnailFile,
            @RequestParam("cardType") @NotBlank(message = "卡片类型不能为空") String cardType,
            @RequestParam("contentId") @NotBlank(message = "内容ID不能为空") String contentId,
            @RequestParam("videoFileName") @NotBlank(message = "视频文件名不能为空") String videoFileName,
            @RequestParam(value = "thumbnailFileName", required = false) String thumbnailFileName,
            @RequestParam(value = "thumbnailSource", required = false) String thumbnailSource,
            @RequestParam(value = "autoGenerateThumbnail", required = false, defaultValue = "false") Boolean autoGenerateThumbnail) {
        
        log.info("========== 接收视频替换请求：cardType={}, contentId={}, videoFileName={}, thumbnailFileName={}, thumbnailSource={}, autoGenerateThumbnail={} ==========",
                cardType, contentId, videoFileName, thumbnailFileName, thumbnailSource, autoGenerateThumbnail);

        try {
            VideoReplaceVO result = videoService.replaceVideo(
                    videoFile, thumbnailFile, cardType, contentId, 
                    videoFileName, thumbnailFileName, thumbnailSource, autoGenerateThumbnail);
            return RetInfo.ok(result);
        } catch (IllegalArgumentException e) {
            log.error("参数错误：{}", e.getMessage());
            return RetInfo.error(400, e.getMessage());
        } catch (Exception e) {
            log.error("视频替换失败：{}", e.getMessage(), e);
            return RetInfo.error(500, "视频替换失败：" + e.getMessage());
        }
    }
}

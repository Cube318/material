package com.cube.material.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cube.material.entity.Video;
import com.cube.material.vo.ThumbnailUploadVO;
import com.cube.material.vo.VideoReplaceVO;
import com.cube.material.vo.VideoUploadVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 视频服务接口
 * @author cube
 */
public interface VideoService extends IService<Video> {

    /**
     * 上传视频文件到OSS
     *
     * @param file 视频文件
     * @param cardType 卡片类型：attraction / food / product
     * @param contentId 内容ID
     * @param fileName 文件名
     * @return 视频上传结果
     */
    VideoUploadVO uploadVideo(MultipartFile file, String cardType, String contentId, String fileName);

    /**
     * 上传起播图文件到OSS
     *
     * @param file 图片文件
     * @param cardType 卡片类型
     * @param contentId 内容ID
     * @param fileName 文件名
     * @param source 起播图来源
     * @return 起播图上传结果
     */
    ThumbnailUploadVO uploadThumbnail(MultipartFile file, String cardType, String contentId, String fileName, String source);

    /**
     * 替换视频完整流程
     *
     * @param videoFile 视频文件
     * @param thumbnailFile 起播图文件（可选）
     * @param cardType 卡片类型
     * @param contentId 内容ID
     * @param videoFileName 视频文件名
     * @param thumbnailFileName 起播图文件名
     * @param thumbnailSource 起播图来源
     * @param autoGenerateThumbnail 是否自动生成起播图
     * @return 视频替换结果
     */
    VideoReplaceVO replaceVideo(
            MultipartFile videoFile,
            MultipartFile thumbnailFile,
            String cardType,
            String contentId,
            String videoFileName,
            String thumbnailFileName,
            String thumbnailSource,
            Boolean autoGenerateThumbnail
    );
}

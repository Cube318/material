package com.cube.material.common;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.cube.material.config.OssConfig;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 阿里云OSS工具类
 * 提供文件上传、删除等功能
 * @author cube
 */
@Slf4j
@Component
public class OssUtil {

    @Resource
    private OssConfig ossConfig;

    /**
     * 上传文件到OSS
     *
     * @param file 文件对象
     * @param objectName OSS对象名称（包含路径，如：videos/attractionVideo/video/test.mp4）
     * @return OSS对象完整URL（使用CDN域名）
     * @throws IOException 上传失败时抛出异常
     */
    public String uploadFile(MultipartFile file, String objectName) throws IOException {
        OSS ossClient = null;
        try {
            // 根据文件类型选择Bucket
            String bucketName = selectBucketByObjectName(objectName);
            log.info("选择的Bucket: {}", bucketName);
            
            // 创建OSS客户端
            ossClient = new OSSClientBuilder().build(
                    ossConfig.getEndpoint(),
                    ossConfig.getAccessKeyId(),
                    ossConfig.getAccessKeySecret()
            );

            // 上传文件流
            InputStream inputStream = file.getInputStream();
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucketName,
                    objectName,
                    inputStream
            );

            // 执行上传
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            log.info("文件上传成功到Bucket[{}]: {}, ETag: {}", bucketName, objectName, result.getETag());

            // 返回CDN访问URL
            return buildCdnUrl(objectName);
        } catch (Exception e) {
            log.error("文件上传失败: {}, 错误信息: {}", objectName, e.getMessage(), e);
            throw new IOException("文件上传失败: " + e.getMessage(), e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 上传InputStream到OSS
     *
     * @param inputStream 输入流
     * @param objectName OSS对象名称
     * @param contentLength 内容长度
     * @return OSS对象完整URL
     * @throws IOException 上传失败时抛出异常
     */
    public String uploadStream(InputStream inputStream, String objectName, long contentLength) throws IOException {
        OSS ossClient = null;
        try {
            // 根据文件类型选择Bucket
            String bucketName = selectBucketByObjectName(objectName);
            log.info("选择的Bucket: {}", bucketName);
            
            ossClient = new OSSClientBuilder().build(
                    ossConfig.getEndpoint(),
                    ossConfig.getAccessKeyId(),
                    ossConfig.getAccessKeySecret()
            );

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucketName,
                    objectName,
                    inputStream
            );

            PutObjectResult result = ossClient.putObject(putObjectRequest);
            log.info("流上传成功到Bucket[{}]: {}, ETag: {}", bucketName, objectName, result.getETag());

            return buildCdnUrl(objectName);
        } catch (Exception e) {
            log.error("流上传失败: {}, 错误信息: {}", objectName, e.getMessage(), e);
            throw new IOException("流上传失败: " + e.getMessage(), e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 删除OSS文件
     *
     * @param objectName OSS对象名称
     */
    public void deleteFile(String objectName) {
        OSS ossClient = null;
        try {
            ossClient = new OSSClientBuilder().build(
                    ossConfig.getEndpoint(),
                    ossConfig.getAccessKeyId(),
                    ossConfig.getAccessKeySecret()
            );

            ossClient.deleteObject(ossConfig.getBucketName(), objectName);
            log.info("文件删除成功: {}", objectName);
        } catch (Exception e) {
            log.error("文件删除失败: {}, 错误信息: {}", objectName, e.getMessage(), e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 根据对象名称选择Bucket
     * 
     * @param objectName OSS对象名称
     * @return Bucket名称
     */
    private String selectBucketByObjectName(String objectName) {
        boolean isVideo = objectName.contains("/video/") || objectName.endsWith(".mp4") || 
                         objectName.endsWith(".mov") || objectName.endsWith(".avi") ||
                         objectName.endsWith(".mkv") || objectName.endsWith(".webm") ||
                         objectName.endsWith(".m3u8") || objectName.endsWith(".ts");
        boolean isImage = objectName.contains("image") || objectName.contains("Thumb") ||
                         objectName.endsWith(".jpg") || objectName.endsWith(".png") || 
                         objectName.endsWith(".jpeg") || objectName.endsWith(".webp");
        
        if (isImage) {
            return ossConfig.getImageBucketName();
        } else {
            // 视频和其他文件默认使用视频Bucket
            return ossConfig.getBucketName();
        }
    }

    /**
     * 根据对象名称构建CDN访问URL
     *
     * @param objectName OSS对象名称
     * @return CDN访问URL
     */
    private String buildCdnUrl(String objectName) {
        log.info("========== 开始构建CDN URL ==========");
        log.info("输入的 objectName: {}", objectName);
        
        // 判断是视频还是图片，使用对应的CDN域名
        String cdnDomain;
        boolean isVideo = objectName.contains("/video/") || objectName.endsWith(".mp4") || 
                         objectName.endsWith(".mov") || objectName.endsWith(".avi") ||
                         objectName.endsWith(".mkv") || objectName.endsWith(".webm");
        boolean isImage = objectName.contains("image") || objectName.contains("Thumb") ||
                         objectName.endsWith(".jpg") || objectName.endsWith(".png") || 
                         objectName.endsWith(".jpeg") || objectName.endsWith(".webp");
        
        log.info("文件类型判断: isVideo={}, isImage={}", isVideo, isImage);
        
        if (isVideo) {
            cdnDomain = ossConfig.getVideoCdnDomain();
            log.info("判断为视频文件，使用视频CDN: {}", cdnDomain);
        } else if (isImage) {
            cdnDomain = ossConfig.getImageCdnDomain();
            log.info("判断为图片文件，使用图片CDN: {}", cdnDomain);
        } else {
            // 默认使用视频CDN
            cdnDomain = ossConfig.getVideoCdnDomain();
            log.info("无法判断类型，使用默认视频CDN: {}", cdnDomain);
        }

        // 移除域名末尾的斜杠（如果有）
        if (cdnDomain.endsWith("/")) {
            cdnDomain = cdnDomain.substring(0, cdnDomain.length() - 1);
            log.info("移除域名末尾斜杠后: {}", cdnDomain);
        }

        // 确保objectName以斜杠开头
        if (!objectName.startsWith("/")) {
            objectName = "/" + objectName;
            log.info("添加开头斜杠后的 objectName: {}", objectName);
        }

        String finalUrl = cdnDomain + objectName;
        log.info("最终生成的 CDN URL: {}", finalUrl);
        log.info("========== CDN URL 构建完成 ==========");
        
        return finalUrl;
    }

    /**
     * 从完整URL中提取OSS对象名称
     *
     * @param url 完整URL
     * @return OSS对象名称
     */
    public String extractObjectName(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }

        // 移除CDN域名部分
        url = url.replace(ossConfig.getVideoCdnDomain(), "");
        url = url.replace(ossConfig.getImageCdnDomain(), "");

        // 移除开头的斜杠
        if (url.startsWith("/")) {
            url = url.substring(1);
        }

        return url;
    }
}

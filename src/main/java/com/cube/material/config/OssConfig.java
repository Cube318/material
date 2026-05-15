package com.cube.material.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云OSS配置类
 * @author cube
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssConfig {

    /**
     * OSS访问密钥ID
     */
    private String accessKeyId;

    /**
     * OSS访问密钥Secret
     */
    private String accessKeySecret;

    /**
     * OSS地域节点
     */
    private String endpoint;

    /**
     * OSS存储桶名称（视频）
     */
    private String bucketName;
    
    /**
     * OSS图片存储桶名称
     */
    private String imageBucketName;

    /**
     * OSS基础路径前缀
     */
    private String basePrefix;

    /**
     * 视频CDN域名
     */
    private String videoCdnDomain;

    /**
     * 图片CDN域名
     */
    private String imageCdnDomain;
}

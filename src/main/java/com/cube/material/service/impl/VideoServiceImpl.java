package com.cube.material.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cube.material.common.OssUtil;
import com.cube.material.entity.Attraction;
import com.cube.material.entity.Goods;
import com.cube.material.entity.Video;
import com.cube.material.mapper.AttractionMapper;
import com.cube.material.mapper.GoodsMapper;
import com.cube.material.mapper.VideoMapper;
import com.cube.material.service.VideoService;
import com.cube.material.util.FfmpegUtil;
import com.cube.material.vo.ThumbnailUploadVO;
import com.cube.material.vo.VideoReplaceVO;
import com.cube.material.vo.VideoUploadVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 视频服务实现类
 * @author cube
 */
@Slf4j
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Resource
    private OssUtil ossUtil;

    @Resource
    private FfmpegUtil ffmpegUtil;

    @Resource
    private AttractionMapper attractionMapper;

    @Resource
    private VideoMapper videoMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Value("${transcode.temp-dir:/tmp/video_transcode}")
    private String tempDir;

    @Value("${transcode.enabled:false}")
    private Boolean transcodeEnabled;

    // 支持的视频格式
    private static final List<String> VIDEO_FORMATS = Arrays.asList("mp4", "mov", "avi", "mkv", "webm");
    
    // 支持的图片格式
    private static final List<String> IMAGE_FORMATS = Arrays.asList("jpg", "jpeg", "png", "webp");
    
    // 文件大小限制
    private static final long MAX_VIDEO_SIZE = 200 * 1024 * 1024; // 200MB
    private static final long MAX_IMAGE_SIZE = 5 * 1024 * 1024;   // 5MB

    @Override
    public VideoUploadVO uploadVideo(MultipartFile file, String cardType, String contentId, String fileName) {
        log.info("========== 开始上传视频文件：cardType={}, contentId={}, fileName={} ==========", 
                 cardType, contentId, fileName);

        File tempVideoFile = null;
        File tempTranscodeDir = null;

        try {
            // 1. 验证文件
            validateVideoFile(file);

            // 2. 检查是否启用转码
            if (Boolean.TRUE.equals(transcodeEnabled)) {
                log.info("转码已启用，开始转码流程");
                
                // 2.1 保存原始视频到临时文件
                String uuid = UUID.randomUUID().toString();
                File tempDirFile = new File(tempDir);
                if (!tempDirFile.exists()) {
                    tempDirFile.mkdirs();
                }
                
                tempVideoFile = new File(tempDir + File.separator + uuid + "_" + fileName);
                file.transferTo(tempVideoFile);
                log.info("临时文件保存成功：{}", tempVideoFile.getAbsolutePath());

                // 2.2 转码为HLS格式
                tempTranscodeDir = new File(tempDir + File.separator + uuid);
                String m3u8Path = ffmpegUtil.transcodeToHls(
                    tempVideoFile.getAbsolutePath(), 
                    tempTranscodeDir.getAbsolutePath()
                );
                log.info("转码完成：{}", m3u8Path);

                // 2.3 上传M3U8和TS文件到OSS
                String videoUrl = uploadHlsToOss(tempTranscodeDir, cardType, contentId, fileName);
                log.info("HLS文件上传成功，M3U8 URL：{}", videoUrl);

                // 2.4 构建返回对象
                return VideoUploadVO.builder()
                        .fileUrl(videoUrl)
                        .fileName(fileName)
                        .fileSize(file.getSize())
                        .duration(null)
                        .resolution("720x1280")
                        .build();

            } else {
                log.info("转码未启用，直接上传原始视频");
                
                // 3. 直接上传原始视频（不转码）
                String objectName = buildVideoObjectName(cardType, contentId, fileName);
                log.info("OSS对象名称：{}", objectName);

                String fileUrl = ossUtil.uploadFile(file, objectName);
                log.info("视频上传成功，URL：{}", fileUrl);

                return VideoUploadVO.builder()
                        .fileUrl(fileUrl)
                        .fileName(fileName)
                        .fileSize(file.getSize())
                        .duration(null)
                        .resolution(null)
                        .build();
            }

        } catch (Exception e) {
            log.error("视频上传失败：{}", e.getMessage(), e);
            throw new RuntimeException("视频上传失败：" + e.getMessage());
        } finally {
            // 清理临时文件
            if (tempVideoFile != null && tempVideoFile.exists()) {
                boolean deleted = tempVideoFile.delete();
                log.info("删除临时视频文件：{}，结果：{}", tempVideoFile.getAbsolutePath(), deleted);
            }
            if (tempTranscodeDir != null && tempTranscodeDir.exists()) {
                ffmpegUtil.deleteDirectory(tempTranscodeDir);
            }
        }
    }

    @Override
    public ThumbnailUploadVO uploadThumbnail(MultipartFile file, String cardType, String contentId, 
                                              String fileName, String source) {
        log.info("========== 开始上传起播图：cardType={}, contentId={}, fileName={}, source={} ==========", 
                 cardType, contentId, fileName, source);

        try {
            // 1. 验证文件
            validateImageFile(file);
            log.info("文件验证通过：大小={}，类型={}", file.getSize(), file.getContentType());

            // 2. 先读取图片尺寸（在上传前）
            Integer width = null;
            Integer height = null;
            try {
                byte[] fileBytes = file.getBytes();
                ByteArrayInputStream bis = new ByteArrayInputStream(fileBytes);
                BufferedImage image = ImageIO.read(bis);
                if (image != null) {
                    width = image.getWidth();
                    height = image.getHeight();
                    log.info("图片尺寸：{}x{}", width, height);
                }
            } catch (Exception e) {
                log.warn("读取图片尺寸失败（不影响上传）：{}", e.getMessage());
            }

            // 3. 构建OSS对象名称
            String objectName = buildThumbnailObjectName(cardType, contentId, fileName);
            log.info("OSS对象名称：{}", objectName);
            log.info("起播图文件名：{}", fileName);
            log.info("卡片类型：{}", cardType);
            log.info("内容ID：{}", contentId);

            // 4. 上传到OSS
            log.info("开始调用 ossUtil.uploadFile()...");
            String fileUrl = ossUtil.uploadFile(file, objectName);
            log.info("起播图上传成功，返回URL：{}", fileUrl);
            log.info("起播图文件大小：{} bytes", file.getSize());

            // 5. 构建返回对象
            return ThumbnailUploadVO.builder()
                    .fileUrl(fileUrl)
                    .fileName(fileName)
                    .fileSize(file.getSize())
                    .width(width)
                    .height(height)
                    .build();

        } catch (Exception e) {
            log.error("起播图上传失败：{}", e.getMessage(), e);
            throw new RuntimeException("起播图上传失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public VideoReplaceVO replaceVideo(MultipartFile videoFile, MultipartFile thumbnailFile, 
                                        String cardType, String contentId, String videoFileName, 
                                        String thumbnailFileName, String thumbnailSource, 
                                        Boolean autoGenerateThumbnail) {
        log.info("========== 开始视频替换流程：cardType={}, contentId={} ==========", cardType, contentId);

        List<VideoReplaceVO.ProcessStep> processSteps = new ArrayList<>();
        String videoUrl = null;
        String thumbnailUrl = null;
        String videoId = null;

        try {
            // 步骤1: 上传视频文件
            log.info("步骤1: 上传视频文件");
            VideoUploadVO videoUploadResult = uploadVideo(videoFile, cardType, contentId, videoFileName);
            videoUrl = videoUploadResult.getFileUrl();
            addProcessStep(processSteps, "upload", "success", "视频上传完成");

            // 步骤2: 处理起播图
            log.info("步骤2: 处理起播图");
            if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
                // 有起播图文件，直接上传
                ThumbnailUploadVO thumbnailUploadResult = uploadThumbnail(
                        thumbnailFile, cardType, contentId, thumbnailFileName, thumbnailSource);
                thumbnailUrl = thumbnailUploadResult.getFileUrl();
                addProcessStep(processSteps, "thumbnail", "success", "起播图上传完成");
            } else if (Boolean.TRUE.equals(autoGenerateThumbnail)) {
                // TODO: 自动生成起播图（截取视频第一帧）
                log.warn("自动生成起播图功能暂未实现，将跳过");
                addProcessStep(processSteps, "thumbnail", "skipped", "自动生成起播图功能暂未实现");
            } else {
                addProcessStep(processSteps, "thumbnail", "skipped", "未提供起播图");
            }

            // 步骤3: 更新数据库
            log.info("步骤3: 更新数据库");
            videoId = updateDatabase(cardType, contentId, videoUrl, thumbnailUrl);
            addProcessStep(processSteps, "database", "success", "数据库更新完成");

            // 构建返回对象
            VideoReplaceVO result = VideoReplaceVO.builder()
                    .videoUrl(videoUrl)
                    .thumbnailUrl(thumbnailUrl)
                    .videoId(videoId)
                    .videoInfo(VideoReplaceVO.VideoInfo.builder()
                            .duration(videoUploadResult.getDuration())
                            .resolution(videoUploadResult.getResolution())
                            .fileSize(videoUploadResult.getFileSize())
                            .format(getFileExtension(videoFileName))
                            .build())
                    .processSteps(processSteps)
                    .build();

            // 添加起播图信息
            if (thumbnailUrl != null) {
                result.setThumbnailInfo(VideoReplaceVO.ThumbnailInfo.builder()
                        .width(null)
                        .height(null)
                        .fileSize(thumbnailFile != null ? thumbnailFile.getSize() : null)
                        .build());
            }

            log.info("========== 视频替换流程完成 ==========");
            return result;

        } catch (Exception e) {
            log.error("视频替换失败：{}", e.getMessage(), e);
            addProcessStep(processSteps, "error", "failed", "视频替换失败：" + e.getMessage());
            throw new RuntimeException("视频替换失败：" + e.getMessage());
        }
    }

    /**
     * 更新数据库
     */
    private String updateDatabase(String cardType, String contentId, String videoUrl, String thumbnailUrl) {
        log.info("更新数据库：cardType={}, contentId={}, videoUrl={}, thumbnailUrl={}", 
                 cardType, contentId, videoUrl, thumbnailUrl);

        switch (cardType.toLowerCase()) {
            case "attraction":
                return updateAttractionVideo(contentId, videoUrl, thumbnailUrl);
            case "goods":
                return updateGoodsVideo(contentId, videoUrl, thumbnailUrl);
            case "food":
                // TODO: 实现美食的视频更新
                log.warn("暂不支持 {} 类型的视频更新", cardType);
                return null;
            default:
                throw new IllegalArgumentException("不支持的卡片类型：" + cardType);
        }
    }

    /**
     * 更新景点视频
     */
    private String updateAttractionVideo(String attractionId, String videoUrl, String thumbnailUrl) {
        // 1. 查询景点是否存在
        Attraction attraction = attractionMapper.selectById(Long.parseLong(attractionId));
        if (attraction == null) {
            throw new RuntimeException("景点不存在：attractionId=" + attractionId);
        }

        // 2. 查询该景点是否已有视频记录
        LambdaQueryWrapper<Video> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Video::getUserId, Integer.parseInt(attractionId))
                   .eq(Video::getFlag, "1")
                   .orderByAsc(Video::getId)
                   .last("LIMIT 1");
        Video existingVideo = videoMapper.selectOne(queryWrapper);

        if (existingVideo != null) {
            // 更新现有视频记录
            log.info("更新现有视频记录，videoId={}", existingVideo.getId());
            existingVideo.setVideoUrl(videoUrl);
            if (thumbnailUrl != null) {
                existingVideo.setThumbnailUrl(thumbnailUrl);
            }
            existingVideo.setUpdateDate(LocalDateTime.now());
            videoMapper.updateById(existingVideo);
            
            // 同时更新景点表的summaryVideoUrl
//            attraction.setSummaryVideoUrl(videoUrl);
//            attractionMapper.updateById(attraction);

            return existingVideo.getId().toString();
        } else {
            // 创建新的视频记录
            log.info("创建新的视频记录");
            Video newVideo = new Video();
            newVideo.setUserId(Integer.parseInt(attractionId));
            newVideo.setTitle(attraction.getName() + "视频");
            newVideo.setVideoUrl(videoUrl);
            newVideo.setThumbnailUrl(thumbnailUrl);
            newVideo.setOrientationType(1); // 默认横屏
            newVideo.setFlag("1"); // 已发布
            newVideo.setCreateDate(LocalDateTime.now());
            newVideo.setUpdateDate(LocalDateTime.now());
            videoMapper.insert(newVideo);
            
            // 同时更新景点表的summaryVideoUrl
//            attraction.setSummaryVideoUrl(videoUrl);
//            attractionMapper.updateById(attraction);
            
            return newVideo.getId().toString();
        }
    }

    /**
     * 更新商品视频
     */
    private String updateGoodsVideo(String goodsId, String videoUrl, String thumbnailUrl) {
        // 1. 查询商品是否存在
        Goods goods = goodsMapper.selectById(Integer.parseInt(goodsId));
        if (goods == null) {
            throw new RuntimeException("商品不存在：goodsId=" + goodsId);
        }

        // 2. 直接更新商品表的视频URL和起播图URL
        log.info("更新商品视频记录，goodsId={}", goodsId);
        goods.setVideoUrl(videoUrl);
        if (thumbnailUrl != null) {
            goods.setCoverImageUrl(thumbnailUrl);
        }
        goods.setUpdateTime(LocalDateTime.now());
        goodsMapper.updateById(goods);

        log.info("商品视频更新完成，goodsId={}, videoUrl={}, coverImageUrl={}", 
                 goodsId, videoUrl, thumbnailUrl);
        
        // 返回商品ID作为标识
        return goodsId;
    }

    /**
     * 验证视频文件
     */
    private void validateVideoFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("视频文件不能为空");
        }

        // 验证文件大小
        if (file.getSize() > MAX_VIDEO_SIZE) {
            throw new IllegalArgumentException("视频文件大小不能超过200MB");
        }

        // 验证文件格式
        String fileName = file.getOriginalFilename();
        if (fileName == null || !isValidVideoFormat(fileName)) {
            throw new IllegalArgumentException("不支持的视频格式，仅支持：" + VIDEO_FORMATS);
        }
    }

    /**
     * 验证图片文件
     */
    private void validateImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("图片文件不能为空");
        }

        // 验证文件大小
        if (file.getSize() > MAX_IMAGE_SIZE) {
            throw new IllegalArgumentException("图片文件大小不能超过5MB");
        }

        // 验证文件格式
        String fileName = file.getOriginalFilename();
        if (fileName == null || !isValidImageFormat(fileName)) {
            throw new IllegalArgumentException("不支持的图片格式，仅支持：" + IMAGE_FORMATS);
        }
    }

    /**
     * 构建视频OSS对象名称
     * 
     * ⚠️ OSS路径配置说明 ⚠️
     * 如需修改视频上传路径，请修改此方法中的 basePath 变量值
     * 
     * 当前配置：
     * - 景点视频：videos/attractionVideo/video/quickTools/
     * - 美食视频：videos/foodVideo/video/quickTools/
     * - 商品视频：videos/m3u8/goods/
     * 
     * 同时需要同步修改：
     * 1. 前端文件：material-system/src/views/cards/VideoReplaceCard.vue
     *    的 generateVideoUrl() 方法中的 basePath
     * 2. API文档：material/API_SUMMARY.md 中的路径说明
     */
    private String buildVideoObjectName(String cardType, String contentId, String fileName) {
        String basePath = switch (cardType.toLowerCase()) {
            case "attraction" -> "videos/attractionVideo/video/quickTools/";
            case "food" -> "videos/foodVideo/video/quickTools/";
            case "goods" -> "videos/m3u8/goods/";
            default -> throw new IllegalArgumentException("不支持的卡片类型：" + cardType);
        };
        return basePath + fileName;
    }

    /**
     * 构建起播图OSS对象名称
     * 
     * ⚠️ OSS路径配置说明 ⚠️
     * 如需修改起播图上传路径，请修改此方法中的 basePath 变量值
     * 
     * 当前配置：
     * - 景点起播图：images/attractionVideo/quickTools/
     * - 美食起播图：images/foodVideo/quickTools/
     * - 商品起播图：images/goods/images/
     * 
     * 同时需要同步修改：
     * 1. 前端文件：material-system/src/views/cards/VideoReplaceCard.vue
     *    的 generateCoverUrl() 方法中的 basePath
     * 2. API文档：material/API_SUMMARY.md 中的路径说明
     */
    private String buildThumbnailObjectName(String cardType, String contentId, String fileName) {
        String basePath = switch (cardType.toLowerCase()) {
            case "attraction" -> "images/attractionVideo/quickTools/";
            case "food" -> "images/foodVideo/quickTools/";
            case "goods" -> "images/goods/images/";
            default -> throw new IllegalArgumentException("不支持的卡片类型：" + cardType);
        };
        return basePath + fileName;
    }

    /**
     * 检查是否为有效的视频格式
     */
    private boolean isValidVideoFormat(String fileName) {
        String extension = getFileExtension(fileName);
        return VIDEO_FORMATS.contains(extension.toLowerCase());
    }

    /**
     * 检查是否为有效的图片格式
     */
    private boolean isValidImageFormat(String fileName) {
        String extension = getFileExtension(fileName);
        return IMAGE_FORMATS.contains(extension.toLowerCase());
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 上传HLS文件到OSS
     * 
     * @param hlsDir HLS目录（包含index.m3u8和多个.ts文件）
     * @param cardType 卡片类型
     * @param contentId 内容ID
     * @param originalFileName 原始文件名
     * @return M3U8文件的URL
     */
    private String uploadHlsToOss(File hlsDir, String cardType, String contentId, 
                                    String originalFileName) throws Exception {
        log.info("开始上传HLS文件到OSS：{}", hlsDir.getAbsolutePath());

        // 去掉扩展名，用作文件夹名
        String fileNameWithoutExt = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
        String basePath = switch (cardType.toLowerCase()) {
            case "attraction" -> "videos/attractionVideo/video/quickTools/";
            case "food" -> "videos/foodVideo/video/quickTools/";
            case "goods" -> "videos/m3u8/goods/";
            default -> throw new IllegalArgumentException("不支持的卡片类型：" + cardType);
        };

        String m3u8Url = null;
        File[] files = hlsDir.listFiles();
        if (files == null || files.length == 0) {
            throw new RuntimeException("HLS目录为空");
        }

        // 上传所有文件
        for (File file : files) {
            if (file.isFile()) {
                String objectName = basePath + fileNameWithoutExt + "/" + file.getName();
                log.info("上传文件：{} -> {}", file.getName(), objectName);
                
                // 读取文件并上传
                try (InputStream fis = Files.newInputStream(file.toPath())) {
                    String url = ossUtil.uploadStream(fis, objectName, file.length());
                    
                    // 记录M3U8文件的URL
                    if (file.getName().equals("index.m3u8")) {
                        m3u8Url = url;
                        log.info("M3U8 URL：{}", m3u8Url);
                    }
                }
            }
        }

        if (m3u8Url == null) {
            throw new RuntimeException("未找到index.m3u8文件");
        }

        log.info("HLS文件上传完成，共{}个文件", files.length);
        return m3u8Url;
    }

    /**
     * 添加处理步骤
     */
    private void addProcessStep(List<VideoReplaceVO.ProcessStep> steps, String step, 
                                 String status, String message) {
        steps.add(VideoReplaceVO.ProcessStep.builder()
                .step(step)
                .status(status)
                .message(message)
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build());
    }
}

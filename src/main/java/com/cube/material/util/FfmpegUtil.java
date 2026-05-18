package com.cube.material.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * FFmpeg工具类
 * 用于视频转码、截图等操作
 * @author cube
 */
@Slf4j
@Component
public class FfmpegUtil {

    /**
     * 转码视频为 HLS 格式（同步方法）
     * 
     * @param inputFilePath 输入文件路径（如：/tmp/video123.mp4）
     * @param outputDir 输出目录（如：/tmp/video123/）
     * @return M3U8文件路径（如：/tmp/video123/index.m3u8）
     */
    public String transcodeToHls(String inputFilePath, String outputDir) throws Exception {
        log.info("========== 开始转码视频：{} -> {} ==========", inputFilePath, outputDir);
        
        // 创建输出目录
        File outDir = new File(outputDir);
        if (!outDir.exists()) {
            boolean created = outDir.mkdirs();
            if (!created) {
                throw new RuntimeException("创建输出目录失败：" + outputDir);
            }
        }

        String m3u8Path = outputDir + File.separator + "index.m3u8";

        // 构建 ffmpeg 命令
        List<String> command = new ArrayList<>();
        command.add("ffmpeg");
        command.add("-i");
        command.add(inputFilePath);
        command.add("-r");
        command.add("24");
        command.add("-b:v");
        command.add("1.5M");
        command.add("-s");
        command.add("720x1280");
        command.add("-vcodec");
        command.add("libx264");
        command.add("-acodec");
        command.add("aac");
        command.add("-strict");
        command.add("-2");
        command.add("-f");
        command.add("hls");
        command.add("-hls_list_size");
        command.add("0");
        command.add("-hls_time");
        command.add("2");
        command.add("-force_key_frames");
        command.add("expr:gte(n,n_forced*48)");
        command.add(m3u8Path);

        log.info("执行转码命令：{}", String.join(" ", command));

        // 执行命令
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        // 读取输出日志
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
                log.debug("FFmpeg: {}", line);
            }
        }

        // 等待完成
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            log.error("FFmpeg转码失败，退出码：{}, 输出：{}", exitCode, output);
            throw new RuntimeException("FFmpeg转码失败，退出码：" + exitCode);
        }

        // 验证输出文件是否存在
        File m3u8File = new File(m3u8Path);
        if (!m3u8File.exists()) {
            throw new RuntimeException("转码完成但M3U8文件不存在：" + m3u8Path);
        }

        log.info("转码完成：{}，文件大小：{} bytes", m3u8Path, m3u8File.length());
        return m3u8Path;
    }

    /**
     * 截取视频第一帧作为起播图
     * 
     * @param inputFilePath 输入视频文件路径
     * @param outputImagePath 输出图片路径（如：/tmp/thumbnail.jpg）
     */
    public void captureFirstFrame(String inputFilePath, String outputImagePath) throws Exception {
        log.info("截取视频第一帧：{} -> {}", inputFilePath, outputImagePath);
        
        List<String> command = new ArrayList<>();
        command.add("ffmpeg");
        command.add("-i");
        command.add(inputFilePath);
        command.add("-ss");
        command.add("00:00:01");
        command.add("-vframes");
        command.add("1");
        command.add(outputImagePath);

        log.info("执行截图命令：{}", String.join(" ", command));

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        // 读取输出日志
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                log.debug("FFmpeg: {}", line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("截取第一帧失败，退出码：" + exitCode);
        }

        // 验证输出文件
        File imageFile = new File(outputImagePath);
        if (!imageFile.exists()) {
            throw new RuntimeException("截图完成但文件不存在：" + outputImagePath);
        }

        log.info("截取第一帧完成：{}，文件大小：{} bytes", outputImagePath, imageFile.length());
    }

    /**
     * 检查FFmpeg是否可用
     * 
     * @return true-可用，false-不可用
     */
    public boolean isFfmpegAvailable() {
        try {
            ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-version");
            Process process = pb.start();
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (Exception e) {
            log.error("FFmpeg不可用：{}", e.getMessage());
            return false;
        }
    }

    /**
     * 删除目录及其所有内容
     * 
     * @param directory 目录路径
     */
    public void deleteDirectory(File directory) {
        if (directory == null || !directory.exists()) {
            return;
        }

        try {
            if (directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isDirectory()) {
                            deleteDirectory(file);
                        } else {
                            Files.deleteIfExists(file.toPath());
                        }
                    }
                }
            }
            Files.deleteIfExists(directory.toPath());
            log.info("删除目录成功：{}", directory.getAbsolutePath());
        } catch (Exception e) {
            log.warn("删除目录失败：{}，错误：{}", directory.getAbsolutePath(), e.getMessage());
        }
    }
}

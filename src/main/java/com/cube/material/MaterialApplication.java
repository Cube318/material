package com.cube.material;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author cube
 */
@SpringBootApplication
@MapperScan("com.cube.material.mapper")  // 指定 Mapper 包路径

public class MaterialApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaterialApplication.class, args);
    }

}

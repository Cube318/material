package com.cube.material.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cube
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "项目启动成功";
    }

}
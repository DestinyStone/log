package com.test.maven.testlog.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author: zhouxiaofeng
 * @create: 2021-04-12 17:02
 * @description:
 **/
@RestController
public class TestController {

    @GetMapping("/test")
    @ApiOperation("test")
    public String test(@RequestParam("id") String id) {
        return "test";
    }

    @PostMapping("/testPost")
    @ApiOperation("testPost")
    public String test(@RequestBody List<Integer> ids) {
       throw new RuntimeException("222");
    }
}
    
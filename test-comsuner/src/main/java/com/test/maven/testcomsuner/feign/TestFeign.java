package com.test.maven.testcomsuner.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "test-provider")
public interface TestFeign {
    @GetMapping("/test")
    public String test();
}

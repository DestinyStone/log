package com.test.maven.testcomsuner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.test.maven.testcomsuner.feign")
public class TestComsunerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestComsunerApplication.class, args);
    }

}

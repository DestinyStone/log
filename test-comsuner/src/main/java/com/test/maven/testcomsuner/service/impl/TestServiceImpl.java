package com.test.maven.testcomsuner.service.impl;

import com.test.maven.testcomsuner.service.TestService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public String test(@NotBlank String id) {
        return id;
    }
}

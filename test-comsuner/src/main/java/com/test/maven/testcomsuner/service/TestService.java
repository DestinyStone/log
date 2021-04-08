package com.test.maven.testcomsuner.service;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
public interface TestService {

    public String test(@NotBlank String id);
}

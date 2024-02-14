package com.nhg.messenger.infrastructure.configuration;

import com.nhg.common.domain.UseCase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "com.nhg.messenger.application",
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION, value = UseCase.class
        )
)
public class ApplicationUseCaseScanConfig { }
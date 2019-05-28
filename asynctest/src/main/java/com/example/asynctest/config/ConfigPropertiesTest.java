package com.example.asynctest.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("mail")
@Data
public class ConfigPropertiesTest {
    private String host;
    private long port;
    private String from;
    private List<String> pets;
}

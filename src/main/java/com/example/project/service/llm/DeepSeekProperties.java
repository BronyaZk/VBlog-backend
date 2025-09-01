package com.example.project.service.llm;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "deepseek")
public class DeepSeekProperties {
    private String baseUrl; // 例如 https://api.deepseek.com/chat/completions (OpenAI兼容)
    private String apiKey;
    private String model = "deepseek-chat"; // 按需替换官方模型名
}



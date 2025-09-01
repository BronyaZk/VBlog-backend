package com.example.project.service.llm;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import com.example.project.common.ex.BusinessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DeepSeekHttpClient implements DeepSeekClient {

    private final DeepSeekProperties properties;
    private final RestClient restClient = RestClient.create();

    @Override
    public String summarize(String prompt) {
        String url = properties.getBaseUrl();
        if (url == null || url.isBlank()) {
            url = "https://api.deepseek.com/v1/chat/completions";
        }

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> body = new HashMap<>();
        body.put("model", properties.getModel());
        body.put("messages", List.of(message));
        body.put("temperature", 0.2);

        Map<?, ?> response;
        try {
            RestClient.RequestBodySpec requestSpec = restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON);
            if (properties.getApiKey() != null && !properties.getApiKey().isEmpty()) {
                requestSpec = requestSpec.header("Authorization", "Bearer " + properties.getApiKey())
                        .header("X-API-Key", properties.getApiKey());
            }
            response = requestSpec
                    .body(body)
                    .retrieve()
                    .body(Map.class);
        } catch (RestClientResponseException e) {
            int status = e.getRawStatusCode();
            String bodyText = e.getResponseBodyAsString();
            if (status == 401) {
                throw new BusinessException("DeepSeek 认证失败，请检查 API Key 是否正确或是否有访问权限");
            }
            String msg = "DeepSeek HTTP错误: status=" + status + ", body=" + bodyText;
            throw new RuntimeException(msg, e);
        }

        try {
            List<?> choices = (List<?>) response.get("choices");
            if (choices != null && !choices.isEmpty()) {
                Object first = choices.get(0);
                if (first instanceof Map<?, ?> firstMap) {
                    Object messageObj = firstMap.get("message");
                    if (messageObj instanceof Map<?, ?> msgMap) {
                        Object content = msgMap.get("content");
                        if (content != null) {
                            return content.toString().trim();
                        }
                    }
                }
            }
        } catch (Exception ignored) {}

        return "提炼失败，未获取到有效结果";
    }
}



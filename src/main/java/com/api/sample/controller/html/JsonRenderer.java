package com.api.sample.controller.html;

import com.api.sample.common.annotation.HtmxRenderer;
import com.api.sample.common.exception.HtmxException;
import com.api.sample.common.tool.Html;
import com.api.sample.common.util.RandomUtils;
import com.api.sample.service.json.JsonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@HtmxRenderer
@RequiredArgsConstructor
public class JsonRenderer {
    private final JsonService jsonService;
    private final ObjectMapper objectMapper;


    @GetMapping("/json-property/input")
    public ResponseEntity<String> jsonPropertyInput() {
        return ResponseEntity.ok(jsonService.getSelectRoot());
    }

    @GetMapping("/json-generate/textarea")
    public ResponseEntity<String> jsonTextarea(@RequestParam Map<String, String> params) throws JsonProcessingException {
        String json = params.get("values");
        // key -> type
        // value -> property
        List<Map<String, String>> properties = objectMapper.readValue(json, new TypeReference<>() {
        });
        if(properties.isEmpty()){
            throw new IllegalArgumentException();
        }
        Map<String, String> result = new HashMap<>();
        for (Map<String, String> property : properties) {
            property.forEach((k, v) -> {
                Function<String, Object> function = jsonService.getFunctionMap().get(k);
                if (function != null) {
                    result.put(v, function.apply(k).toString());
                } else {
                    throw new HtmxException(Html.javaScript("openDialog('에러','비정상적인 접근입니다.')"));
                }
            });
        }
        Html root = Html.createRoot("textarea",
                "style", "resize:none; width:400px; height:600px;",
                "text", objectMapper.writeValueAsString(result),
                "readonly", "true"
        );
        return ResponseEntity.ok(root.toString());
    }
    @PostMapping("/save-properties/script")
    public ResponseEntity<String> saveProperties(@RequestParam Map<String, String> params) throws JsonProcessingException {
        String json = params.get("values");
        List<Map<String, String>> properties = objectMapper.readValue(json, new TypeReference<>() {});
        jsonService.saveJsonProperties(properties);
        Html js = Html.javaScript("openDialog('성공', '저장 완료')");
        return ResponseEntity.ok(js.toString());
    }
    @GetMapping("/load-properties/textarea")
    public ResponseEntity<String> loadProperties() {
        Html jsonTextarea = jsonService.loadJsonProperties();
        return ResponseEntity.ok(jsonTextarea.toString());
    }

}

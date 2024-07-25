package com.api.sample.controller.html;

import com.api.sample.common.annotation.HtmxController;
import com.api.sample.common.tool.Html;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@HtmxController
@RequiredArgsConstructor
public class JsonController {
    private final ObjectMapper objectMapper;
    private String selectRoot;
    private static final Map<String, Function<String, Object>> functionMap = new LinkedHashMap<>();

    @PostConstruct
    private void init() {
        functionMap.put("String", (arg) -> "random string");
        functionMap.put("Number", (arg) -> 999);
        functionMap.put("Double", (arg) -> 0.5);
        setSelectRoot();
    }

    private void setSelectRoot() {
        Html root = Html.createRoot("select");
        functionMap.keySet().forEach(type -> {
            root.putLast("option", "text", type);
        });
        root.append("input", "type", "text");
        selectRoot = root.toString();
    }


    @GetMapping("/json-property/input")
    public String jsonPropertyInputRenderer() {
        return selectRoot;
    }

    @PostMapping("/json-generator/textarea")
    public String jsonTextareaRenderer(@RequestParam(required = false) Map<String, String> params) throws JsonProcessingException {
        String json = params.get("values");
        List<Map<String, String>> values = objectMapper.readValue(json, new TypeReference<>() {
        });
        for (Map<String, String> value : values) {
            Map<String, String> updatedValue = new HashMap<>();

            // 기존 맵의 모든 엔트리를 순회합니다.
            value.forEach((k, v) -> {
                Function<String, Object> function = functionMap.get(k);
                if (function != null) {
                    // 키를 값으로, 변환된 값을 키로 설정합니다.
                    updatedValue.put(v, function.apply(k).toString());
                } else {
                    // 변환 함수가 없을 경우, 원래 값을 그대로 유지합니다.
                    updatedValue.put(k, v);
                }
            });
            value.clear();
            value.putAll(updatedValue);
        }
        Html root = Html.createRoot("textarea",
                "style", "resize:none; width:400px; height:600px;",
                "text", objectMapper.writeValueAsString(values),
                "readonly", "true"
        );
        return root.toString();
    }
}

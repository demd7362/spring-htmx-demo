package com.api.sample.controller.html;

import com.api.sample.common.annotation.HtmxController;
import com.api.sample.common.tool.Html;
import com.api.sample.common.util.RandomUtils;
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
        functionMap.put("핸드폰 번호", (arg) -> RandomUtils.generatePhoneNumber());
        functionMap.put("사람 이름", (arg) -> RandomUtils.generateKoreanName());
        functionMap.put("주민등록번호", (arg) -> RandomUtils.generateRegistrationNumber());
        setSelectRoot();
    }

    private void setSelectRoot() {
        Html root = Html.createRoot("select");
        functionMap.keySet().forEach(type -> {
            root.putLast("option", "text", type);
        });
        root.append("input", "type", "text",
                "placeholder", "Key"
                );
        selectRoot = root.toString();
    }


    @GetMapping("/json-property/input")
    public String jsonPropertyInputRenderer() {
        return selectRoot;
    }

    @PostMapping("/json-generator/textarea")
    public String jsonTextareaRenderer(@RequestParam(required = false) Map<String, String> params) throws JsonProcessingException {
        String json = params.get("values");
        // key -> type
        // value -> property
        List<Map<String, String>> values = objectMapper.readValue(json, new TypeReference<>() {
        });
        if(values.isEmpty()){
            throw new IllegalArgumentException();
        }
        Map<String, String> result = new HashMap<>();
        for (Map<String, String> value : values) {
            value.forEach((k, v) -> {
                Function<String, Object> function = functionMap.get(k);
                if (function != null) {
                    result.put(v, function.apply(k).toString());
                } else {
                    result.put(k, v);
                }
            });
        }
        Html root = Html.createRoot("textarea",
                "style", "resize:none; width:400px; height:600px;",
                "text", objectMapper.writeValueAsString(result),
                "readonly", "true"
        );
        return root.toString();
    }
}

package com.api.sample.controller.html;

import com.api.sample.common.annotation.HtmxController;
import com.api.sample.common.tool.Html;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.UUID;

@Slf4j
@HtmxController
@RequiredArgsConstructor
public class JsonController {
    private final ObjectMapper objectMapper;

    @GetMapping("/json-property/input")
    public String jsonPropertyInputRenderer() {
        Html select = Html.createRoot("select");
        select.putLast("option","text","Test");
        log.info("{}" ,select.toString());
        select.append("input", "type", "text", "name" , UUID.randomUUID().toString());
        return select.toString();
    }

    @PostMapping("/json-generator/textarea")
    public String jsonTextareaRenderer(@RequestParam(required = false) Map<String, String> params) throws JsonProcessingException {
        log.info("{}", params);
        Html root = Html.createRoot("textarea",
                "style", "resize:none; width:400px; height:600px;",
                "text", objectMapper.writeValueAsString(params),
                "readonly", "true"
        );
        return root.toString();
    }
}

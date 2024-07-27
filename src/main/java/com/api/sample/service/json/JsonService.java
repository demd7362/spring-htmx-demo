package com.api.sample.service.json;

import com.api.sample.common.exception.HtmxException;
import com.api.sample.common.tool.Html;
import com.api.sample.common.util.RandomUtils;
import com.api.sample.common.util.SecurityUtils;
import com.api.sample.entity.user.JsonProperty;
import com.api.sample.entity.user.User;
import com.api.sample.repository.JsonPropertyRepository;
import io.micrometer.core.instrument.binder.logging.LogbackMetrics;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JsonService {
    private final JsonPropertyRepository jsonPropertyRepository;
    @Getter
    private final Map<String, Function<String, Object>> functionMap = new LinkedHashMap<>();
    private final LogbackMetrics logbackMetrics;
    @Getter
    private String selectRoot;

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

    @Transactional
    public void saveJsonProperties(List<Map<String, String>> properties) {
        if(properties.isEmpty()){
            throw new IllegalArgumentException();
        }
        User user = SecurityUtils.currentUser();
        jsonPropertyRepository.deleteAllByUserId(user.getId());
        List<JsonProperty> jsonProperties = new ArrayList<>();
        for (Map<String, String> property : properties) {
            for (var entry : property.entrySet()) {
                String type = entry.getKey();
                String attribute = entry.getValue();
                JsonProperty jsonProperty = JsonProperty.builder()
                        .type(type)
                        .attribute(attribute)
                        .user(user)
                        .build();
                jsonProperties.add(jsonProperty);
            }
        }
        jsonPropertyRepository.saveAll(jsonProperties);
    }

    @Transactional(readOnly = true)
    public Html loadJsonProperties() {
        User user = SecurityUtils.currentUser();
        List<JsonProperty> jsonProperties = jsonPropertyRepository.findAllByUserId(user.getId());
        if (jsonProperties.isEmpty()) {
            throw new HtmxException(Html.javaScript("openDialog('불러오기','저장된 값이 없습니다.')"));
        }
        Html root = null;
        for (int i = 0; i < jsonProperties.size(); i++) {
            JsonProperty jsonProperty = jsonProperties.get(i);
            Html select = Html.createRoot("select");
            if(i == 0){
                root = select;
            } else {
                root.append(select);
            }
            functionMap.keySet().forEach(type -> {
                if (type.equals(jsonProperty.getType())) {
                    select.putLast("option", "text", type, "selected", "");
                } else {
                    select.putLast("option", "text", type);
                }
            });
            select.append("input", "type", "text",
                    "placeholder", "Key",
                    "value", jsonProperty.getAttribute()
            );
        }
        return root;
    }
}

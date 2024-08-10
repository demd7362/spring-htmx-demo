package com.api.sample.service.subway;

import com.api.sample.http.subway.SubwayHttpInterface;
import com.api.sample.model.SubwayResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubwayService {
    private final SubwayHttpInterface subwayHttpInterface;
    @PostConstruct
    public void get() {
        SubwayResponse result = subwayHttpInterface.send(1, 10);
        log.info("[{}]", result);
    }
}

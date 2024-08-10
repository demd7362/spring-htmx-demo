package com.api.sample.http.subway;

import com.api.sample.model.SubwayResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/15071311/v1/uddi:9aff0ee6-26e7-42c4-af0c-84bf31680ca9")
public interface SubwayHttpInterface {
    @GetExchange
    SubwayResponse send(@RequestParam("page") int page, @RequestParam("perPage") int perPage);

}

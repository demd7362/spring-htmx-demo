package com.api.sample.common.exception;

import com.api.sample.common.tool.Html;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class HtmxException extends RuntimeException{

    private final Html html;

    @Override
    public String toString() {
        return this.html.toString();
    }
}

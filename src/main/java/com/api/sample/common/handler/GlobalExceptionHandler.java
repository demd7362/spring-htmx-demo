package com.api.sample.common.handler;

import com.api.sample.common.exception.HtmxException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HtmxException.class)
    public ResponseEntity<String> handleHtmxException(HtmxException e) {
        return ResponseEntity.ok(e.toString());
    }
}

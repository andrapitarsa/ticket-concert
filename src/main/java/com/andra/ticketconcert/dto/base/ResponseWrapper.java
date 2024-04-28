package com.andra.ticketconcert.dto.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseWrapper<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> ResponseWrapper<T> empty() {
        return success(null);
    }

    public static <T> ResponseWrapper<T> success(T data) {
        return ResponseWrapper.<T>builder()
                .message("SUCCESS!")
                .data(data)
                .success(true)
                .build();
    }

    public static <T> ResponseWrapper<T> success() {
        return ResponseWrapper.<T>builder()
                .message("SUCCESS!")
                .data(null)
                .success(true)
                .build();
    }

    public static <T> ResponseWrapper<T> error() {
        return ResponseWrapper.<T>builder()
                .message("ERROR!")
                .success(false)
                .build();
    }
}

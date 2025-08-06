package com.DevWhiz.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiErrorResponse {

    private int status;
    private String message;
    private List<FieldError> errors;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FieldError{
        private String field;
        private String message;
    }

}

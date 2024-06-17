package com.example.assessment.infrastructure.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

public class Error {
    @Schema(description = "Http status code", example = "400")
    private HttpStatus code;
    @Schema(description = "Reason of the error", example = "Bad date format")
    private String reason;

    public Error(HttpStatus code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

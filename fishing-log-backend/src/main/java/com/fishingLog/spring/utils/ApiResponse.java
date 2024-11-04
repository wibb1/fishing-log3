package com.fishingLog.spring.utils;

import java.util.List;
import java.util.Map;

public class ApiResponse {
    private int statusCode;
    private Map<String, List<String>> headers;
    private String body;
    private boolean hasErrors;
    private Map<String, String> errors;

    public ApiResponse(int statusCode, Map<String, List<String>> headers, String body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
        this.hasErrors = false;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean hasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
        this.hasErrors = !errors.isEmpty();
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
               "statusCode=" + statusCode +
               ", headers=" + headers +
               ", body='" + body + '\'' +
               ", hasErrors=" + hasErrors +
               ", errors=" + errors +
               '}';
    }
}

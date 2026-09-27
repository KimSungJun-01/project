package com.project.global.exception;

public record ApiErrorResponse(int status, String code, String message) {
}

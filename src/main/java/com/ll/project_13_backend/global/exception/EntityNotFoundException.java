package com.ll.project_13_backend.global.exception;


public class EntityNotFoundException extends BusinessException {
    private ErrorCode errorCode;
    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}

package com.antrianservice.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CustomArgsException extends CommonException {
    protected Object[] args;
    public CustomArgsException(String errorCode, Object... args) {
        this.errorCode = errorCode;
        this.args = args;
    }
}
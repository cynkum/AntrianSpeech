package com.antrianservice.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommonException extends RuntimeException{
    protected String errorCode;

    public CommonException (String errorCode) {
        this.errorCode = errorCode;
    }
}

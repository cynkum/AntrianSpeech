package com.example.demo.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TempBaseResponse {
    protected String errorCode;
    protected ErrorMessageBaseResponse errorMessageBaseResponse;
    protected Date date;

    public void setDefaultTimestamp(){
        this.date = date;
    }
}

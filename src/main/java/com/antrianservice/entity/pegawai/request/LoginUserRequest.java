package com.antrianservice.entity.pegawai.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.Valid;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Valid
public class LoginUserRequest {
    private String username;
    private String password;
}

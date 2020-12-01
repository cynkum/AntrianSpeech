package com.example.demo.entity.pegawai;

import com.example.demo.response.ErrorSchema;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoginUser {
    private ErrorSchema errorSchema;
    private LoginView loginView;

    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class LoginView{
        private String username;
        private String password;
    }
}

package com.antrianservice.entity.pegawai.response;

import com.antrianservice.response.ErrorSchema;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PostLoginOutput {
    private PostLoginResponse postLoginResponse;
    private ErrorSchema errorSchema;
}

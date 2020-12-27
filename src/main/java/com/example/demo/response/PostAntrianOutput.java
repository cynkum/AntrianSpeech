package com.example.demo.response;

import com.example.demo.entity.antrian.response.PostAntrianResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PostAntrianOutput {
    private PostAntrianResponse postAntrianResp;
    private ErrorSchema errorSchema;
}

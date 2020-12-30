package com.antrianservice.entity.kategori.response;

import com.antrianservice.response.ErrorSchema;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PostKategoriOutput {
    private PostKategoriResponse postKategoriResponse;
    private ErrorSchema errorSchema;

}

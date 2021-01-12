package com.antrianservice.entity.kategori.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PostKategoriRequest {
    private String idCabang;
    private String jenisAntrian;
    private String kodeKategori;
    private String kodeSpeech;
}

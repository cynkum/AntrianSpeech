package com.example.demo.entity.antrian.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PostAntrianRequest {
    private String idKategori;
    private String namaNasabah;
    private String tanggalAntri;
    private String statusAntrian;
}

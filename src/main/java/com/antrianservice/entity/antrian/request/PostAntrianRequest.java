package com.antrianservice.entity.antrian.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import com.google.gson.annotations.SerializedName;
import javax.validation.constraints.NotBlank;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PostAntrianRequest {
    @NotBlank
    private String idKategori;
    @NotBlank
    private String namaNasabah;
    @NotBlank
    private String tanggalAntri;
    @NotBlank
    private String statusAntrian;
}

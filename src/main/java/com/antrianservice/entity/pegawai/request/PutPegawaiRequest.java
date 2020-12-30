package com.antrianservice.entity.pegawai.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PutPegawaiRequest {
    private String idCabang;
    private String namaPegawai;
    private String hakAkses;
    private String username;
    private String password;
}

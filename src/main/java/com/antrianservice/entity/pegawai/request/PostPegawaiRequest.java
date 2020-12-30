package com.antrianservice.entity.pegawai.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PostPegawaiRequest {
    private String nip;
    private String idCabang;
    private String namaPegawai;
    private String hakAkses;
    private String username;
    private String password;
}

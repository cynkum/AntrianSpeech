package com.antrianservice.model;

import com.antrianservice.repository.PegawaiRepository;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
@Table(name = "pegawai")
public class Pegawai {
    @Id
    @Column(name = "nip", unique = true)
    private String nip;
    @NotNull
    @Column(name = "id_cabang")
    private String idCabang;
    @NotNull
    @Column(name = "nama_pegawai")
    private String namaPegawai;
    @NotNull
    @Column(name = "hak_akses")
    private String hakAkses;
    @NotNull
    @Column(name = "username")
    private String username;
    @NotNull
    @Column(name = "password")
    private String password;

    public Pegawai(String nip, String idCabang, String namaPegawai, String hakAkses, String username, String password){
        this.nip = nip;
        this.idCabang = idCabang;
        this.namaPegawai = namaPegawai;
        this.hakAkses = hakAkses;
        this.username = username;
        this.password = password;
    }
    public Pegawai(){

    }


    public Pegawai(PegawaiRepository pegawaiRepository) {
    }
}

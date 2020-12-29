package com.antrianservice.model;

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
@Table(name = "kategori_antrian")
public class Kategori {
    @Id
    @Column(name = "id_kategori", unique = true)
    private String idKategori;
    @NotNull
    @Column(name = "id_cabang")
    private String idCabang;
    @NotNull
    @Column(name = "jenis_antrian")
    private String jenisAntrian;
    @Column(name = "kode_kategori")
    private String kodeKategori;

    public Kategori(String idKategori, String idCabang, String jenisAntrian, String kodeKategori){
        this.idKategori = idKategori;
        this.idCabang = idCabang;
        this.jenisAntrian = jenisAntrian;
        this.kodeKategori = kodeKategori;
    }

    public Kategori() {

    }
}

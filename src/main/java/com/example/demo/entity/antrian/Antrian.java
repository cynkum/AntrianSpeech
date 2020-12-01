package com.example.demo.entity;

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
import java.util.Date;

@Entity
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
@Getter
@Setter
@Table(name = "antrian")
public class Antrian {
    @Id
    @Column(name = "id_antrian", unique = true)
    private int idAntrian;
    @NotNull
    @Column(name = "id_kategori")
    private String idKategori;
    @NotNull
    @Column(name = "nip")
    private String nip;
    @NotNull
    @Column(name = "nomor_antrian")
    private String nomorAntrian;
    @NotNull
    @Column(name = "nama_nasabah")
    private String namaNasabah;
    @NotNull
    @Column(name = "tanggal_antri")
    private Date tanggalAntri;
    @NotNull
    @Column(name = "status_antrian")
    private String statusAntrian;

//    public void setTanggal(Date tanggalAntri) {
//        this.tanggalAntri = tanggalAntri;
//    }

}

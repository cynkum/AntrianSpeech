package com.antrianservice.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "history")
@Entity
@Data
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_history", unique = true)
    private int idHistory;
    @NotNull
    @Column(name = "nip")
    private String nip;
    @NotNull
    @Column(name = "id_kategori")
    private String idKategori;
    @NotNull
    @Column(name = "tanggal")
    private Date tanggal;
    @NotNull
    @Column(name = "nama_nasabah")
    private String namaNasabah;
    @NotNull
    @Column(name = "status_antrian")
    private String statusAntrian;

    public History(int idHistory, String nip, String idKategori, Date tanggal, String namaNasabah, String statusAntrian) {
        this.idHistory = idHistory;
        this.nip = nip;
        this.idKategori = idKategori;
        this.tanggal = tanggal;
        this.namaNasabah = namaNasabah;
        this.statusAntrian = statusAntrian;
    }

    public History(){

    }

}


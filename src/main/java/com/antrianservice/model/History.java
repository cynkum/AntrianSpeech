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
import java.util.Date;

@Table(name = "history")
@Entity
@Data
public class History {
    @Id
    @Column(name = "id_history", unique = true)
    private String id_history;
    @NotNull
    @Column(name = "nip")
    private String nip;
    @NotNull
    @Column(name = "id_kategori")
    private String id_kategori;
    @NotNull
    @Column(name = "tanggal")
    private Date tanggal;
    @NotNull
    @Column(name = "start")
    private Date start;
    @NotNull
    @Column(name = "finish")
    private Date finish;

    public History(String id_history, String nip, String id_kategori, Date tanggal, Date start, Date finish ){
        this.id_history = id_history;
        this.nip = nip;
        this.id_kategori = id_kategori;
        this.tanggal = tanggal;
        this.start = start;
        this.finish = finish;
    }

    public History(){

    }

}

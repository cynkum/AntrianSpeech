package com.antrianservice.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
@Table(name = "cabang")
public class Cabang {
    @Id
    @Column(name = "id_cabang")
    private String idCabang;
    @NotNull
    @Column(name = "nama_cabang")
    private String namaCabang;

    public Cabang(String idCabang, String namaCabang){
        this.idCabang=idCabang;
        this.namaCabang=namaCabang;
    }

    public Cabang() {

    }
}

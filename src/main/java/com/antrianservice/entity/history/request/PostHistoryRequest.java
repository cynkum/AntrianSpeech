package com.antrianservice.entity.history.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Date;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PostHistoryRequest {
   private int idAntrian;
   private String nip;
   private String idKategori;
   private Date tanggal;
   private Date start;
   private Date finish;

}

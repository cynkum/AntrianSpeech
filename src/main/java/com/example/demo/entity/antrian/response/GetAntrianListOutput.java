package com.example.demo.entity.antrian.response;

import com.example.demo.response.ErrorSchema;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetAntrianListOutput {
    private GetAntrianListResponse outputSchema;
    private ErrorSchema errorSchema;

    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class AntrianView{
        private int idAntrian;
        private String idKategori;
        private String nip;
        private String nomorAntrian;
        private String namaNasabah;
        private Date tanggalAntri;
        private String statusAntrian;
    }
    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class GetAntrianListResponse{
        private List<AntrianView> antrianView;
    }
}

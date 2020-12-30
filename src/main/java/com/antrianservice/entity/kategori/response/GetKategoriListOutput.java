package com.antrianservice.entity.kategori.response;

import com.antrianservice.response.ErrorSchema;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetKategoriListOutput {
    private GetKategoriListResponse outputSchema;
    private ErrorSchema errorSchema;

    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class KategoriView{
        private String idKategori;
        private String idCabang;
        private String jenisAntrian;
        private String kodeKategori;
        private String kodeSpeech;
    }
    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class GetKategoriListResponse{
        private List<KategoriView> kategoriList;
    }
}

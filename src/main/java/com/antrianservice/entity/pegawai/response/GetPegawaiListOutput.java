package com.antrianservice.entity.pegawai.response;

import com.antrianservice.response.ErrorSchema;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetPegawaiListOutput {
    private GetPegawaiListResponse outputSchema;
    private ErrorSchema errorSchema;

    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class PegawaiView{
        private String nip;
        private String idCabang;
        private String namaPegawai;
        private String hakAkses;
        private String username;
        private String password;
    }
    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class GetPegawaiListResponse{
        private List<PegawaiView> pegawaiList;
    }
}

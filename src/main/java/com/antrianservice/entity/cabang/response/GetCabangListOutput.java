package com.antrianservice.entity.cabang.response;

import com.antrianservice.entity.pegawai.response.GetPegawaiListOutput;
import com.antrianservice.response.ErrorSchema;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetCabangListOutput {
    private GetCabangListOutput.GetCabangListResponse outputSchema;
    private ErrorSchema errorSchema;

    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class CabangView{
        private String idCabang;
        private String namaCabang;
    }
    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class GetCabangListResponse{
        private List<GetCabangListOutput.CabangView> cabangList;
    }
}

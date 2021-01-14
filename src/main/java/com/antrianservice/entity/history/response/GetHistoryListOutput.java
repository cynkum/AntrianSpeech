package com.antrianservice.entity.history.response;

import com.antrianservice.response.ErrorSchema;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetHistoryListOutput {
    private GetHistoryListResponse outputSchema;
    private ErrorSchema errorSchema;

    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class HistoryView{
        private int idHistory;
        private String nip;
        private String idKategori;
        private Date tanggal;
    }
    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class GetHistoryListResponse{
        private List<HistoryView> historyList;
    }

}


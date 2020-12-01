package com.example.demo.entity.pegawai;

import com.example.demo.response.ErrorSchema;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;
@Data
@JsonNaming
public class GetUserInfo {
    private GetUserInfo.GetUserInfoResponse outputSchema;
    private ErrorSchema errorSchema;

    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class UserView{
        private String nip;
        private String idCabang;
//        private String namaCabang;
        private String namaPegawai;
        private String hakAkses;
    }
    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class GetUserInfoResponse{
        private List<GetUserInfo.UserView> userList;
    }
}

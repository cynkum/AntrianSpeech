package com.antrianservice.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ErrorMessageBaseResponse {
    private String indonesian;
    private String english;

    public ErrorMessageBaseResponse(String errMsgIn, String errMsgEn){
        this.indonesian = errMsgIn;
        this.english = errMsgEn;
    }
}

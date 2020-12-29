package com.antrianservice.response;

import com.antrianservice.constant.Constant;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonInclude(value= JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ErrorSchema {
    private String errorCode;
    private ErrorMessageBaseResponse errorMessage;

    public void setResponse(TempBaseResponse tempBaseResponse){
        this.errorCode = tempBaseResponse.getErrorCode();
        this.errorMessage = tempBaseResponse.getErrorMessageBaseResponse();
    }

    public void setSuccessResponse(){
        ErrorMessageBaseResponse errorMessageBaseResponse = new ErrorMessageBaseResponse(Constant.ErrorMessage.Id.SUCCESS,Constant.ErrorMessage.En.SUCCESS);
        this.errorCode = Constant.ErrorCode.SUCCESS;
        this.errorMessage = errorMessageBaseResponse;
    }
}

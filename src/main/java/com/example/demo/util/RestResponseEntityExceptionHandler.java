package com.example.demo.util;

import com.example.demo.constant.Constant;
import com.example.demo.exception.CommonException;
import com.example.demo.response.BaseResponse;
import com.example.demo.response.ErrorMessageBaseResponse;
import com.example.demo.response.ErrorSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.MessageFormat;
import java.util.Hashtable;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private MessageUtils messageUtils;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        System.out.println("RESTRESPONSEENTITIYEXCEPTIONHANDLER handleMethodArgumentNotValid");

        String field="";
        String msgKey="";
        Hashtable<String,String> hashtableSingle = new Hashtable<>();

        ObjectError err = ex.getBindingResult().getAllErrors().get(0);
        field = ((FieldError) err).getField();

        msgKey = err.getDefaultMessage();
        hashtableSingle = messageUtils.getErrMsgTable(msgKey);


        BaseResponse resp = new BaseResponse();
        String pathField = StringUtils.capitalize(field.substring(field.lastIndexOf('.')+1));

        String msgEn = MessageFormat.format(hashtableSingle.get(Constant.ErrorDictionary.ERROR_MSG_EN_KEY),pathField);
        String msgIn = MessageFormat.format(hashtableSingle.get(Constant.ErrorDictionary.ERROR_MSG_IN_KEY),pathField);
        ErrorMessageBaseResponse errorMessageBaseResponse = new ErrorMessageBaseResponse(msgIn,msgEn);
        ErrorSchema errorSchema = new ErrorSchema();
        errorSchema.setErrorMessage(errorMessageBaseResponse);
        errorSchema.setErrorCode(Constant.ErrorCode.VALIDATION_FAILED);
        resp.setErrorSchema(errorSchema);
        return new ResponseEntity<>(resp,new HttpHeaders(), HttpStatus.OK);
    }

    // For 4xx exception
    @ExceptionHandler(CommonException.class)
    public final ResponseEntity<Object> handlerCommonException(CommonException ex, WebRequest request) {
        log.error(ex.getMessage());
        ex.printStackTrace();

        BaseResponse exResponse = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        errorSchema.setResponse(messageUtils.setResponseCustomException(ex));
        exResponse.setErrorSchema(errorSchema);
        return new ResponseEntity<>(exResponse,new HttpHeaders(),HttpStatus.OK);
    }
}

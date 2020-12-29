package com.antrianservice.util;

import com.antrianservice.constant.Constant;
import com.antrianservice.constant.SingletonHashtable;
import com.antrianservice.exception.CommonException;
import com.antrianservice.exception.CustomArgsException;
import com.antrianservice.repository.ErrorMessageRepository;
import com.antrianservice.response.ErrorMessageBaseResponse;
import com.antrianservice.response.TempBaseResponse;
import com.antrianservice.response.ErrorDictionary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Hashtable;

@Component
@Slf4j
public class MessageUtils {
    @Autowired
    private SingletonHashtable singletonHashtable;

    @Autowired
    private ErrorMessageRepository errorMessageRepository;

    public Hashtable<String, String> getErrMsgTable(String keyMessage) throws CommonException {
        Hashtable<String, String> errCodeHashtable = null;
        if (singletonHashtable.getHashtable(keyMessage) == null) {
            log.debug("Hashtable null with key message : " + keyMessage);
            Iterable<ErrorDictionary> errorDictionaryIterable = errorMessageRepository.findAll();

            String sKey = "";
            String sErrCode = "";

            for (ErrorDictionary errorDictionary : errorDictionaryIterable) {
                Hashtable<String, String> recordErrMsgDB = new Hashtable<>();
                sErrCode = errorDictionary.getErrCode();
                sKey = errorDictionary.getKeyMessage();
                recordErrMsgDB.put(Constant.ErrorDictionary.ERROR_CODE_KEY, sErrCode);
                recordErrMsgDB.put(Constant.ErrorDictionary.ERROR_MSG_IN_KEY, errorDictionary.getErrorMsgIn()==null?"NULL":errorDictionary.getErrorMsgIn());
                recordErrMsgDB.put(Constant.ErrorDictionary.ERROR_MSG_EN_KEY, errorDictionary.getErrorMsgEn()==null?"NULL":errorDictionary.getErrorMsgEn());

                singletonHashtable.setHashtable(sKey, recordErrMsgDB);
            }
            errCodeHashtable = singletonHashtable.getHashtable(keyMessage);
        } else{
                log.debug("Hashtable exist with key message : "+keyMessage);
                errCodeHashtable = singletonHashtable.getHashtable(keyMessage);
            }
            return errCodeHashtable;
    }
    public TempBaseResponse resolveBaseResponse(TempBaseResponse tempBaseResponse, String errorCode, Hashtable<String, String> errorRecordDB, Object[] args){
        String errMsgInFormatted="";
        String errMsgEnFormatted="";

        System.out.println("Base Response : "+tempBaseResponse);
        System.out.println("ErrorCode : "+errorCode);
        System.out.println("Error Code : "+errorRecordDB.get(Constant.ErrorDictionary.ERROR_CODE_KEY).toString());

        if(args==null || args.length <= 0){
            errMsgEnFormatted = errorRecordDB.get(Constant.ErrorDictionary.ERROR_MSG_EN_KEY);
            errMsgInFormatted = errorRecordDB.get(Constant.ErrorDictionary.ERROR_MSG_IN_KEY);
        }else{
            errMsgEnFormatted = MessageFormat.format(errorRecordDB.get(Constant.ErrorDictionary.ERROR_MSG_EN_KEY),args);
            errMsgInFormatted = MessageFormat.format(errorRecordDB.get(Constant.ErrorDictionary.ERROR_MSG_IN_KEY),args);
        }

        ErrorMessageBaseResponse errorMessageBaseResponse = new ErrorMessageBaseResponse(errMsgInFormatted, errMsgEnFormatted);
        tempBaseResponse.setErrorCode(errorCode);
        tempBaseResponse.setErrorMessageBaseResponse(errorMessageBaseResponse);
        return tempBaseResponse;
    }

    public TempBaseResponse setResponse(String errorCode, Object[] args){
        TempBaseResponse response = new TempBaseResponse();
        Hashtable<String, String> errorRecordDB;
        errorRecordDB = getErrMsgTable(Constant.SERVICE_NAME + "."+errorCode);
        if(errorRecordDB==null){
            System.out.println("Error Code get from Error.errorcode");
            errorRecordDB = getErrMsgTable("Error."+errorCode);
            if(errorRecordDB==null){
                errorRecordDB = getErrMsgTable("Error");
            }
        }
        System.out.println("Before Resolve : "+response.toString());
        response = resolveBaseResponse(response,errorCode,errorRecordDB,args);
        System.out.println("After Resolve : "+response.toString());
        response.setDefaultTimestamp();
        return response;
    }

    public TempBaseResponse setResponseCustomException(CommonException ex) {
        if(ex instanceof CustomArgsException) {
            return setResponse(ex.getErrorCode(), ((CustomArgsException)ex).getArgs());
        }
        return setResponse(ex.getErrorCode(), null);
    }

}

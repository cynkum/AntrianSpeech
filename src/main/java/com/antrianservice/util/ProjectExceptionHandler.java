//package com.example.demo.util;
//
//import com.example.demo.constant.Constant;
//import com.example.demo.response.BaseResponse;
//import com.example.demo.response.ErrorSchema;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.StringUtils;
//import org.springframework.validation.FieldError;
//import org.springframework.validation.ObjectError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//
//@ControllerAdvice
//@Slf4j
//public class ProjectExceptionHandler extends ResponseEntityExceptionHandler {
//    @Autowired
//    MessageUtils messageUtils;
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
//
//        ObjectError err = ex.getBindingResult().getAllErrors().get(0);
//        String field = ((FieldError) err).getField();
//        String errorKey = StringUtils.capitalize(err.getDefaultMessage());
//        String pathField = StringUtils.capitalize(field.substring(field.lastIndexOf('.')+1));
//        log.debug( errorKey +"|"+pathField);
//        String msgEn = Constant.ErrorMessage.En.GENERAL_FAILED;
//        String msgIn =  Constant.ErrorMessage.Id.GENERAL_FAILED;
//        BaseResponse baseResponse=new BaseResponse();
//
//        ErrorSchema errorSchema = new ErrorSchema();
//        errorSchema.setResponse(messageUtils.setResponse("699",new String[]{pathField}));
//        baseResponse.setErrorSchema(errorSchema);
//
//        return new ResponseEntity<>(baseResponse,
//                HttpStatus.BAD_REQUEST);
//    }
//}

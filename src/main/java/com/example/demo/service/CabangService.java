package com.example.demo.service;
import com.example.demo.entity.cabang.Cabang;
import com.example.demo.exception.CommonException;
import com.example.demo.exception.CustomArgsException;
import com.example.demo.repository.CabangRepository;
import com.example.demo.repository.ErrorMessageRepository;
import com.example.demo.response.BaseResponse;
import com.example.demo.response.ErrorSchema;
import com.example.demo.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CabangService {
    @Autowired
    CabangRepository cabangRepository;
    @Autowired
    MessageUtils messageUtils;
    @Autowired
    ErrorMessageRepository errorMessageRepository;

    public BaseResponse postCabang(String idCabang, String namaCabang) throws Exception{
        BaseResponse response = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        Cabang cabang = new Cabang();
        try {
            if(cabangRepository.existsById(idCabang)){
                throw new CustomArgsException("300", "idCabang");
            }
            if(idCabang==null){
                throw new CustomArgsException("699.not_empty", "idCabang");
            }
            if(namaCabang==null){
                throw new CustomArgsException("699.not_empty", "namaNabang");
            }
            //List<Cabang> cabangList=cabangRepository.findAllByIdCabang(idCabang);
            cabang.setIdCabang(idCabang);
            cabang.setNamaCabang(namaCabang);
            cabangRepository.save(cabang);
            errorSchema.setSuccessResponse();
            response.setErrorSchema(errorSchema);
        } catch (CommonException e) {
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            response.setErrorSchema(errorSchema);
        } catch (Exception e) {
            Object[] args = new Object[]{"Post Cabang"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(), args));
            response.setErrorSchema(errorSchema);
        }
        return response;
    }

    public Iterable<Cabang> getAllCabang(){
        return cabangRepository.findAll();
    }

    public BaseResponse updateCabang(String idCabang, String namaCabang){
        BaseResponse response = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        Cabang cabang = cabangRepository.findById(idCabang).get();
        try {
            if(idCabang==null){
                throw new CustomArgsException("699.not_empty", "idCabang");
            }
//            if(!cabangRepository.existsById(cabang.getIdCabang())){
//                throw new CustomArgsException("300", "id_cabang");
//            }
            //List<Cabang> cabangList=cabangRepository.findAllByIdCabang(idCabang);
            cabang.setIdCabang(idCabang);
            cabang.setNamaCabang(namaCabang);
            cabangRepository.save(cabang);
            errorSchema.setSuccessResponse();
            response.setErrorSchema(errorSchema);
        } catch (CommonException e) {
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            response.setErrorSchema(errorSchema);
        } catch (Exception e) {
            Object[] args = new Object[]{"Put Cabang"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(), args));
            response.setErrorSchema(errorSchema);
        }
        return response;
    }

    public BaseResponse deleteCabang(String idCabang){
        Cabang cabang = new Cabang();
        BaseResponse response = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        try{
            if(idCabang==null){
                throw new CustomArgsException("699.not_empty", "idCabang");
            }
            cabangRepository.deleteById(idCabang);
            errorSchema.setSuccessResponse();
            response.setErrorSchema(errorSchema);
        } catch (CommonException e){
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            response.setErrorSchema(errorSchema);
        } catch(Exception e){
            Object[] args = new Object[]{"Delete Cabang"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(),args));
            response.setErrorSchema(errorSchema);
        }
        return response;
    }
}

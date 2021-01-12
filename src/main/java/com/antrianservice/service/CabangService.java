package com.antrianservice.service;
import com.antrianservice.entity.cabang.request.PostCabangRequest;
import com.antrianservice.entity.cabang.request.PutCabangRequest;
import com.antrianservice.entity.cabang.response.GetCabangListOutput;
import com.antrianservice.entity.pegawai.response.GetPegawaiListOutput;
import com.antrianservice.exception.CommonException;
import com.antrianservice.exception.CustomArgsException;
import com.antrianservice.model.Pegawai;
import com.antrianservice.repository.CabangRepository;
import com.antrianservice.repository.ErrorMessageRepository;
import com.antrianservice.response.ErrorSchema;
import com.antrianservice.model.Cabang;
import com.antrianservice.response.BaseResponse;
import com.antrianservice.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

@Service
public class CabangService {
    @Autowired
    CabangRepository cabangRepository;
    @Autowired
    MessageUtils messageUtils;
    @Autowired
    ErrorMessageRepository errorMessageRepository;

    public BaseResponse postCabang(PostCabangRequest request) throws Exception{
        BaseResponse response = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        Cabang cabang = new Cabang();
        try {
            if(request.getNamaCabang()==null){
                throw new CustomArgsException("699.not_empty", "namaNabang");
            }
            Long idCabangLast = cabangRepository.countCabang();
            String idCabang = String.valueOf(idCabangLast + 1);
            String idCabangFormat = String.format("%3s", idCabang).replace(" ", "0");
            String kodeCabang = "BCA";
            String idCabangNow = kodeCabang + idCabangFormat;

            cabang.setIdCabang(idCabangNow);
            cabang.setNamaCabang(request.getNamaCabang());
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

    public GetCabangListOutput getCabangListOutput(){
        List<Cabang> cabangList;
        List<GetCabangListOutput.CabangView> cabangViewList=new ArrayList<>();
        GetCabangListOutput response = new GetCabangListOutput();
        GetCabangListOutput.GetCabangListResponse getCabangListResponse=new GetCabangListOutput.GetCabangListResponse();
        ErrorSchema errorSchema= new ErrorSchema();
        try{
            cabangList = cabangRepository.findAll();

            for(Cabang cabang : cabangList){
                GetCabangListOutput.CabangView cabangView = new GetCabangListOutput.CabangView();
                cabangView.setIdCabang(cabang.getIdCabang());
                cabangView.setNamaCabang(cabang.getNamaCabang());
                cabangViewList.add(cabangView);
            }
            getCabangListResponse.setCabangList(cabangViewList);
            response.setOutputSchema(getCabangListResponse);
            errorSchema.setSuccessResponse();
            response.setErrorSchema(errorSchema);
        } catch(CommonException e){
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            response.setErrorSchema(errorSchema);
        } catch(Exception e){
            Object[] args = new Object[]{"Get Cabang List"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(), args));
            response.setErrorSchema(errorSchema);
        }
        return response;
    }
    public BaseResponse updateCabang(String idCabang, PutCabangRequest request){
        BaseResponse response = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        Cabang cabang = cabangRepository.findById(idCabang).get();
        try {
            if(idCabang==null){
                throw new CustomArgsException("699.not_empty", "idCabang");
            }
            cabang.setIdCabang(idCabang);
            cabang.setNamaCabang(request.getNamaCabang());
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

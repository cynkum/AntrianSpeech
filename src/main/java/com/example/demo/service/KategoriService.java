package com.example.demo.service;

import com.example.demo.entity.kategori.GetKategoriListOutput;
import com.example.demo.entity.kategori.Kategori;
import com.example.demo.exception.CommonException;
import com.example.demo.exception.CustomArgsException;
import com.example.demo.repository.CabangRepository;
import com.example.demo.repository.ErrorMessageRepository;
import com.example.demo.repository.KategoriRepository;
import com.example.demo.response.BaseResponse;
import com.example.demo.response.ErrorSchema;
import com.example.demo.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KategoriService {
    @Autowired
    KategoriRepository kategoriRepository;
    @Autowired
    CabangRepository cabangRepository;
    @Autowired
    MessageUtils messageUtils;
    @Autowired
    ErrorMessageRepository errorMessageRepository;

    public BaseResponse postKategori(String idKategori, String idCabang, String jenisAntrian, String kodeKategori) throws Exception{
        BaseResponse response = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        Kategori kategori = new Kategori();
        try {
            if(kategoriRepository.existsById(idKategori)){
                throw new CustomArgsException("300", "id_kategori");
            }
            if(idKategori==null){
                throw new CustomArgsException("699.not_empty", "idKategori");
            }
            if(idCabang==null){
                throw new CustomArgsException("699.not_empty", "idCabang");
            }
            if(jenisAntrian==null){
                throw new CustomArgsException("699.not_empty", "jenisAntrian");
            }
            //List<Cabang> cabangList=cabangRepository.findAllByIdCabang(idCabang);
            kategori.setIdKategori(idKategori);
            kategori.setIdCabang(idCabang);
            kategori.setJenisAntrian(jenisAntrian);
            kategori.setKodeKategori(kodeKategori);
            kategoriRepository.save(kategori);
            errorSchema.setSuccessResponse();
            response.setErrorSchema(errorSchema);
        } catch (CommonException e) {
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            response.setErrorSchema(errorSchema);
        } catch (Exception e) {
            Object[] args = new Object[]{"Post Kategori"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(), args));
            response.setErrorSchema(errorSchema);
        }
        return response;
    }

    public Iterable<Kategori> getAllKategori(){
        return kategoriRepository.findAll();
    }


    public GetKategoriListOutput getKategoriByIdCabang(String idCabang){
        List<Kategori> kategoriList;
        List<GetKategoriListOutput.KategoriView> kategoriViewList=new ArrayList<>();
        GetKategoriListOutput response = new GetKategoriListOutput();
        GetKategoriListOutput.GetKategoriListResponse getKategoriListResponse=new GetKategoriListOutput.GetKategoriListResponse();
        ErrorSchema errorSchema= new ErrorSchema();
        try{
            if(idCabang==null){
                throw new CustomArgsException("699.not_empty", "idCabang");
            }
            if(!cabangRepository.existsById(idCabang)){
                throw new CustomArgsException("699.not_valid", "idCabang");
            }
            kategoriList = kategoriRepository.findAllByIdCabang(idCabang);
            for(Kategori kategori : kategoriList){
                GetKategoriListOutput.KategoriView kategoriView = new GetKategoriListOutput.KategoriView();
                kategoriView.setIdKategori(kategori.getIdKategori());
                kategoriView.setIdCabang(kategori.getIdCabang());
                kategoriView.setJenisAntrian(kategori.getJenisAntrian());
                kategoriView.setKodeKategori(kategori.getKodeKategori());
                kategoriViewList.add(kategoriView);
            }
            getKategoriListResponse.setKategoriList(kategoriViewList);
            response.setOutputSchema(getKategoriListResponse);
            errorSchema.setSuccessResponse();
            response.setErrorSchema(errorSchema);
        } catch(CommonException e){
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            response.setErrorSchema(errorSchema);
        } catch(Exception e){
            Object[] args = new Object[]{"Get Kategori by Id Cabang"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(), args));
            response.setErrorSchema(errorSchema);
        }
        return response;
    }
    public BaseResponse updateKategori(String idKategori, String idCabang, String jenisAntrian, String kodeKategori){
        BaseResponse response = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        Kategori kategori = kategoriRepository.findById(idKategori).get();
        try {
            if(idKategori==null){
                throw new CustomArgsException("699.not_empty", "idKategori");
            }
            kategori.setIdCabang(idCabang);
            kategori.setJenisAntrian(jenisAntrian);
            kategori.setKodeKategori(kodeKategori);
            kategoriRepository.save(kategori);
            errorSchema.setSuccessResponse();
            response.setErrorSchema(errorSchema);
        } catch (CommonException e) {
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            response.setErrorSchema(errorSchema);
        } catch (Exception e) {
            Object[] args = new Object[]{"Put Kategori"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(), args));
            response.setErrorSchema(errorSchema);
        }
        return response;
    }

    public BaseResponse deleteKategori(String idKategori){
        Kategori kategori = new Kategori();
        BaseResponse response = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        try{
            if(idKategori==null){
                throw new CustomArgsException("699.not_empty", "idKategori");
            }
            kategoriRepository.deleteById(idKategori);
            errorSchema.setSuccessResponse();
            response.setErrorSchema(errorSchema);
        } catch (CommonException e){
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            response.setErrorSchema(errorSchema);
        } catch(Exception e){
            Object[] args = new Object[]{"Delete Kategori"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(),args));
            response.setErrorSchema(errorSchema);
        }
        return response;
    }
}

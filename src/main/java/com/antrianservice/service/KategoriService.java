package com.antrianservice.service;

import com.antrianservice.entity.kategori.request.PostKategoriRequest;
import com.antrianservice.entity.kategori.request.PutKategoriRequest;
import com.antrianservice.entity.kategori.response.PostKategoriOutput;
import com.antrianservice.entity.kategori.response.PostKategoriResponse;
import com.antrianservice.model.Kategori;
import com.antrianservice.exception.CommonException;
import com.antrianservice.exception.CustomArgsException;
import com.antrianservice.repository.CabangRepository;
import com.antrianservice.repository.ErrorMessageRepository;
import com.antrianservice.repository.KategoriRepository;
import com.antrianservice.response.ErrorSchema;
import com.antrianservice.entity.kategori.response.GetKategoriListOutput;
import com.antrianservice.response.BaseResponse;
import com.antrianservice.util.MessageUtils;
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

    public PostKategoriOutput postKategori(PostKategoriRequest request) throws Exception{
        PostKategoriOutput postOutput = new PostKategoriOutput();
        PostKategoriResponse postResponse = new PostKategoriResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        Kategori kategori = new Kategori();
        try {
            if(kategoriRepository.existsById(request.getIdKategori())){
                throw new CustomArgsException("300", "id_kategori");
            }
            if(request.getIdKategori()==null){
                throw new CustomArgsException("699.not_empty", "idKategori");
            }
            if(request.getIdCabang()==null){
                throw new CustomArgsException("699.not_empty", "idCabang");
            }
            if(request.getJenisAntrian()==null){
                throw new CustomArgsException("699.not_empty", "jenisAntrian");
            }
            if(request.getKodeKategori()==null){
                throw new CustomArgsException("699.not_empty", "kodeKategori");
            }
            //List<Cabang> cabangList=cabangRepository.findAllByIdCabang(idCabang);
            kategori.setIdKategori(request.getIdKategori());
            kategori.setIdCabang(request.getIdCabang());
            kategori.setJenisAntrian(request.getJenisAntrian());
            kategori.setKodeKategori(request.getKodeKategori());
            kategori.setKodeSpeech(request.getKodeSpeech());
            kategoriRepository.save(kategori);
            List<Kategori> kategoriList = kategoriRepository.findByIdCabangAndAndKodeKategori(request.getIdCabang(), request.getKodeKategori());
            postResponse.setIdKategori(kategoriList.get(0).getIdKategori());
            errorSchema.setSuccessResponse();
            postOutput.setErrorSchema(errorSchema);
            postOutput.setPostKategoriResponse(postResponse);
        } catch (CommonException e) {
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            postOutput.setErrorSchema(errorSchema);
        } catch (Exception e) {
            Object[] args = new Object[]{"Post Kategori"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(), args));
            postOutput.setErrorSchema(errorSchema);
        }
        return postOutput;
    }

    public GetKategoriListOutput getKategoriListOutput(){
        List<Kategori> kategoriList;
        List<GetKategoriListOutput.KategoriView> kategoriViewList=new ArrayList<>();
        GetKategoriListOutput response = new GetKategoriListOutput();
        GetKategoriListOutput.GetKategoriListResponse getKategoriListResponse=new GetKategoriListOutput.GetKategoriListResponse();
        ErrorSchema errorSchema= new ErrorSchema();
        try{
            kategoriList = kategoriRepository.findAll();
            for(Kategori kategori : kategoriList){
                GetKategoriListOutput.KategoriView kategoriView = new GetKategoriListOutput.KategoriView();
                kategoriView.setIdKategori(kategori.getIdKategori());
                kategoriView.setIdCabang(kategori.getIdCabang());
                kategoriView.setJenisAntrian(kategori.getJenisAntrian());
                kategoriView.setKodeKategori(kategori.getKodeKategori());
                kategoriView.setKodeSpeech(kategori.getKodeSpeech());
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
            Object[] args = new Object[]{"Get Kategori"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(), args));
            response.setErrorSchema(errorSchema);
        }
        return response;
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
                kategoriView.setKodeSpeech(kategori.getKodeSpeech());
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
    public BaseResponse updateKategori(String idKategori, PutKategoriRequest request){
        BaseResponse response = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        Kategori kategori = kategoriRepository.findById(idKategori).get();
        try {
            if(idKategori==null){
                throw new CustomArgsException("699.not_empty", "idKategori");
            }
            kategori.setIdCabang(request.getIdCabang());
            kategori.setJenisAntrian(request.getJenisAntrian());
            kategori.setKodeKategori(request.getKodeKategori());
            kategori.setKodeSpeech(request.getKodeSpeech());
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

package com.example.demo.service;

import android.speech.SpeechRecognizer;
import com.example.demo.entity.Antrian;
import com.example.demo.entity.Pegawai;
import com.example.demo.entity.antrian.GetAntrianListOutput;
import com.example.demo.entity.pegawai.GetPegawaiListOutput;
import com.example.demo.exception.CommonException;
import com.example.demo.exception.CustomArgsException;
import com.example.demo.repository.AntrianRepository;
import com.example.demo.repository.ErrorMessageRepository;
import com.example.demo.repository.KategoriRepository;
import com.example.demo.repository.PegawaiRepository;
import com.example.demo.response.BaseResponse;
import com.example.demo.response.ErrorSchema;
import com.example.demo.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AntrianService {
    @Autowired
    AntrianRepository antrianRepository;
    @Autowired
    KategoriRepository kategoriRepository;
    @Autowired
    PegawaiRepository pegawaiRepository;
    @Autowired
    MessageUtils messageUtils;
    @Autowired
    ErrorMessageRepository errorMessageRepository;

    private SpeechRecognizer speechRecognizer;

    public BaseResponse postAntrian(String idKategori, String nomorAntrian, String namaNasabah, String tanggalAntri, String statusAntrian) throws Exception{
        BaseResponse response = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        Antrian antrian = new Antrian();
        //GetPegawaiListOutput.PegawaiView pegawai = new GetPegawaiListOutput.PegawaiView();
        String nip = null;
        //di website pegawai manggil nasabah baru nip keisi/update nip

        int idAntrian;
        try {
            if(idKategori==null){
                throw new CustomArgsException("699.not_empty", "idKategori");
            }
            //List<Cabang> cabangList=cabangRepository.findAllByIdCabang(idCabang);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

            antrian.setIdKategori(idKategori);
            antrian.setNomorAntrian(nomorAntrian);
            antrian.setNamaNasabah(namaNasabah);
            antrian.setTanggalAntri(sdf.parse(tanggalAntri));
            antrian.setStatusAntrian(statusAntrian);
            antrianRepository.save(antrian);
            errorSchema.setSuccessResponse();
            response.setErrorSchema(errorSchema);
        } catch (CommonException e) {
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            response.setErrorSchema(errorSchema);
        } catch (Exception e) {
            Object[] args = new Object[]{"Post Antrian"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(), args));
            response.setErrorSchema(errorSchema);
        }
        return response;
    }

    public Iterable<Antrian> getAllAntrian(){
        return antrianRepository.findAll();
    }


    public GetAntrianListOutput getAntrianByKategori (String idKategori){
        List<Antrian> antrianList;
        List<GetAntrianListOutput.AntrianView> antrianViewList=new ArrayList<>();
        GetAntrianListOutput response = new GetAntrianListOutput();
        GetAntrianListOutput.GetAntrianListResponse getAntrianListResponse=new GetAntrianListOutput.GetAntrianListResponse();
        ErrorSchema errorSchema= new ErrorSchema();
        try{
            if(idKategori==null){
                throw new CustomArgsException("699.not_empty", "idKategori");
            }
//            if(!antrianRepository.existByIdKategori(idKategori)){
//                throw new CustomArgsException("699.not_valid", "idKategori");
//            }
            antrianList = antrianRepository.findAllByIdKategori(idKategori);
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for(Antrian antrian : antrianList){
                GetAntrianListOutput.AntrianView antrianView = new GetAntrianListOutput.AntrianView();
                antrianView.setIdAntrian(antrian.getIdAntrian());
                antrianView.setIdKategori(antrian.getIdKategori());
                antrianView.setNip(antrian.getNip());
                antrianView.setNomorAntrian(antrian.getNomorAntrian());
                antrianView.setNamaNasabah(antrian.getNamaNasabah());
                antrianView.setTanggalAntri(antrian.getTanggalAntri());
                antrianView.setStatusAntrian(antrian.getStatusAntrian());
                antrianViewList.add(antrianView);
            }
            getAntrianListResponse.setAntrianView(antrianViewList);
            response.setOutputSchema(getAntrianListResponse);
            errorSchema.setSuccessResponse();
            response.setErrorSchema(errorSchema);
        } catch(CommonException e){
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            response.setErrorSchema(errorSchema);
        } catch(Exception e){
            Object[] args = new Object[]{"Get Antrian by Id Kategori"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(), args));
            response.setErrorSchema(errorSchema);
        }
        return response;
    }
    public BaseResponse updateAntrianStatus(int idAntrian, String statusAntrian){
        BaseResponse response = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        Antrian antrian = antrianRepository.findById(idAntrian).get();
        try {
            if(!antrianRepository.existsById(idAntrian)){
                throw new CustomArgsException("699.not_valid", "idKategori");
            }
            antrian.setStatusAntrian(statusAntrian);
            antrianRepository.save(antrian);
            errorSchema.setSuccessResponse();
            response.setErrorSchema(errorSchema);
        } catch (CommonException e) {
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            response.setErrorSchema(errorSchema);
        } catch (Exception e) {
            Object[] args = new Object[]{"Put Antrian Status"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(), args));
            response.setErrorSchema(errorSchema);
        }
        return response;
    }

    public BaseResponse deleteAntrian(int idAntrian){
        Antrian antrian = new Antrian();
        BaseResponse response = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        try{
            if(!antrianRepository.existsById(idAntrian)){
                throw new CustomArgsException("699.not_empty", "idKategori");
            }
            antrianRepository.deleteById(idAntrian);
            errorSchema.setSuccessResponse();
            response.setErrorSchema(errorSchema);
        } catch (CommonException e){
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            response.setErrorSchema(errorSchema);
        } catch(Exception e){
            Object[] args = new Object[]{"Delete Antrian"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(),args));
            response.setErrorSchema(errorSchema);
        }
        return response;
    }
}

package com.antrianservice.service;

import com.antrianservice.entity.antrian.request.PutAntrianRequest;
import com.antrianservice.entity.kategori.response.GetKategoriListOutput;
import com.antrianservice.model.Cabang;
import com.antrianservice.model.History;
import com.antrianservice.model.Antrian;
import com.antrianservice.entity.antrian.response.GetAntrianListOutput;
import com.antrianservice.entity.antrian.response.PostAntrianOutput;
import com.antrianservice.entity.antrian.response.PostAntrianResponse;
import com.antrianservice.exception.CommonException;
import com.antrianservice.exception.CustomArgsException;
import com.antrianservice.model.Kategori;
import com.antrianservice.repository.*;
import com.antrianservice.response.ErrorSchema;
import com.antrianservice.util.MessageUtils;
import com.antrianservice.entity.antrian.request.PostAntrianRequest;
import com.antrianservice.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
    CabangRepository cabangRepository;
    @Autowired
    HistoryRepository historyRepository;
    @Autowired
    MessageUtils messageUtils;
    @Autowired
    ErrorMessageRepository errorMessageRepository;

    public PostAntrianOutput postAntrian(PostAntrianRequest request) throws Exception{
        PostAntrianOutput postOutput = new PostAntrianOutput();
        ErrorSchema errorSchema = new ErrorSchema();
        Antrian antrian = new Antrian();
        Cabang cabang = new Cabang();
        Kategori kategori = new Kategori();
        PostAntrianResponse postResponse = new PostAntrianResponse();
        KategoriService kategoriService = new KategoriService();

        //di website pegawai manggil nasabah baru nip keisi/update nip
        try {
            if(!kategoriRepository.existsById(request.getIdKategori())){
                throw new CustomArgsException("699.not_valid", "idKategori");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar cal = Calendar.getInstance();
            String strDate = sdf.format(cal.getTime());

            Long nomorAntrianLast = antrianRepository.countAntrianByIdKategori(request.getIdKategori());
            String nomor = String.valueOf(nomorAntrianLast + 1);
            String number = String.format("%3s", nomor).replace(" ", "0");
            String kodeKategori = kategoriRepository.findKodeKategori(request.getIdKategori());
            String nomorAntrian = kodeKategori + number;

            antrian.setIdKategori(request.getIdKategori());
            antrian.setNomorAntrian(nomorAntrian);
            antrian.setNamaNasabah(request.getNamaNasabah());
            antrian.setTanggalAntri(sdf.parse(strDate));
            antrian.setStatusAntrian("Menunggu dilayani");
            antrianRepository.save(antrian);

            postResponse.setNomorAntrian(nomorAntrian);
            errorSchema.setSuccessResponse();
            postOutput.setErrorSchema(errorSchema);
            postOutput.setPostAntrianResp(postResponse);
        } catch (CommonException e) {
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            postOutput.setErrorSchema(errorSchema);
        } catch (Exception e) {
            Object[] args = new Object[]{"Post Antrian"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(), args));
            postOutput.setErrorSchema(errorSchema);
        }
        return postOutput;
    }

    public GetAntrianListOutput getAntrianListOutput(){
        List<Antrian> antrianList;
        //Log.d("getData", idKategori);
        List<GetAntrianListOutput.AntrianView> antrianViewList=new ArrayList<>();
        GetAntrianListOutput response = new GetAntrianListOutput();
        GetAntrianListOutput.GetAntrianListResponse getAntrianListResponse=new GetAntrianListOutput.GetAntrianListResponse();
        ErrorSchema errorSchema= new ErrorSchema();
        try{
            antrianList = antrianRepository.findAll();
            for(Antrian antrian : antrianList){
                GetAntrianListOutput.AntrianView antrianView = new GetAntrianListOutput.AntrianView();
                antrianView.setIdAntrian(antrian.getIdAntrian());
                antrianView.setIdKategori(antrian.getIdKategori());
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

    public Long getNomorAntrian(String id_kategori) {
        return antrianRepository.countAntrianByIdKategori(id_kategori);
    }

    public GetAntrianListOutput getAntrianByKategori(String idKategori, boolean isToday){
        List<Antrian> antrianList;
        List<GetAntrianListOutput.AntrianView> antrianViewList=new ArrayList<>();
        GetAntrianListOutput response = new GetAntrianListOutput();
        GetAntrianListOutput.GetAntrianListResponse getAntrianListResponse=new GetAntrianListOutput.GetAntrianListResponse();
        ErrorSchema errorSchema= new ErrorSchema();

        String statusAntrian = "Menunggu dilayani";
        try {
                if (isToday == true) {
                        antrianList = antrianRepository.findAntrianByTanggalAntri(idKategori, statusAntrian);
                        if(antrianRepository.countKategori(idKategori)==0){
                            throw new CustomArgsException("699.not_valid", "idKategori");
                        }else{
                        for (Antrian antrian : antrianList) {
                            GetAntrianListOutput.AntrianView antrianView = new GetAntrianListOutput.AntrianView();
                            antrianView.setIdAntrian(antrian.getIdAntrian());
                            antrianView.setIdKategori(antrian.getIdKategori());
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
                    }
                } else {
                    antrianList = antrianRepository.findAllByIdKategori(idKategori);
                    if(antrianRepository.countKategori(idKategori)==0){
                        throw new CustomArgsException("699.not_valid", "idKategori");
                    }else{
                        for (Antrian antrian : antrianList) {
                            GetAntrianListOutput.AntrianView antrianView = new GetAntrianListOutput.AntrianView();
                            antrianView.setIdAntrian(antrian.getIdAntrian());
                            antrianView.setIdKategori(antrian.getIdKategori());
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
                    }
                }
            }catch(CommonException e){
                e.printStackTrace();
                errorSchema.setResponse(messageUtils.setResponseCustomException(e));
                response.setErrorSchema(errorSchema);
            } catch(Exception e){
                Object[] args = new Object[]{"Get Antrian by Tanggal Antri"};
                errorSchema.setResponse(messageUtils.setResponse(e.getMessage(), args));
                response.setErrorSchema(errorSchema);
            }

        return response;
        }


    public BaseResponse updateAntrian(int idAntrian, PutAntrianRequest request){
        BaseResponse response = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        Antrian antrian = antrianRepository.findById(idAntrian).get();
        try {
            if(!antrianRepository.existsById(idAntrian)){
                throw new CustomArgsException("699.not_valid", "idAntrian");
            }
            antrian.setStatusAntrian(request.getStatusAntrian());
            antrianRepository.save(antrian);
            errorSchema.setSuccessResponse();
            response.setErrorSchema(errorSchema);
        } catch (CommonException e) {
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            response.setErrorSchema(errorSchema);
        } catch (Exception e) {
            Object[] args = new Object[]{"Put Antrian"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(), args));
            response.setErrorSchema(errorSchema);
        }
        return response;
    }

    public BaseResponse deleteAntrian(int idAntrian){
        Antrian antrian = new Antrian();
        History history = new History();
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

    public BaseResponse removeDataAntrian() {
        BaseResponse response = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();

        antrianRepository.removeData();
        errorSchema.setSuccessResponse();
        response.setErrorSchema(errorSchema);
        return response;
    }
}

package com.example.demo.service;

import com.example.demo.entity.Antrian;
import com.example.demo.entity.History;
import com.example.demo.entity.antrian.GetAntrianListOutput;
import com.example.demo.entity.antrian.request.PostAntrianRequest;
import com.example.demo.entity.antrian.response.PostAntrianResponse;
import com.example.demo.exception.CommonException;
import com.example.demo.exception.CustomArgsException;
import com.example.demo.repository.*;
import com.example.demo.response.BaseResponse;
import com.example.demo.response.ErrorSchema;
import com.example.demo.response.PostAntrianOutput;
import com.example.demo.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;

import java.text.SimpleDateFormat;
import java.util.*;
//@Log4j2
@Service
public class AntrianService {
    @Autowired
    AntrianRepository antrianRepository;
    @Autowired
    KategoriRepository kategoriRepository;
    @Autowired
    PegawaiRepository pegawaiRepository;
    @Autowired
    HistoryRepository historyRepository;
    @Autowired
    MessageUtils messageUtils;
    @Autowired
    ErrorMessageRepository errorMessageRepository;
    @Autowired
    private Environment env;

    public PostAntrianOutput postAntrian(PostAntrianRequest request) throws Exception{
        PostAntrianOutput postOutput = new PostAntrianOutput();
        ErrorSchema errorSchema = new ErrorSchema();
        Antrian antrian = new Antrian();
        PostAntrianResponse postResponse = new PostAntrianResponse();
        String nip = null;
        //di website pegawai manggil nasabah baru nip keisi/update nip
        String nomorAntrian = null;
        try {
            if(request.getIdKategori()==null){
                throw new CustomArgsException("699.not_empty", "idKategori");
            }
            //List<Cabang> cabangList=cabangRepository.findAllByIdCabang(idCabang);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            Long nomorAntrianLast=antrianRepository.findAllByNomorAntrian(request.getIdKategori());
            String nomor= String.valueOf(nomorAntrianLast+1);
            int nomorAntri2 = nomor.length();
            int length;
            int jmlKategori = 0;
            for (int i = 0; i < 3 - nomorAntri2; i++) {
                nomor = "0" + nomor;
            }
            if (request.getIdKategori().equals("1")) {
                nomor = "A" +nomor;
                antrian.setNomorAntrian(nomor);
            } else if (request.getIdKategori().equals("2")) {
                nomor = "B" +nomor;
                antrian.setNomorAntrian(nomor);
            } else if (request.getIdKategori().equals("3")) {
                nomor = "C" +nomor;
                antrian.setNomorAntrian(nomor);
            } else if (request.getIdKategori().equals("4")) {
                nomor = "D" +nomor;
                antrian.setNomorAntrian(nomor);
            } else {
                antrian.setNomorAntrian("tidak terdaftar");
            }
//            log.debug(nomor);
//            log.debug(nomorAntri2);

            antrian.setIdKategori(request.getIdKategori());
            antrian.setNamaNasabah(request.getNamaNasabah());
            antrian.setTanggalAntri(sdf.parse(request.getTanggalAntri()));
            antrian.setStatusAntrian(request.getStatusAntrian());
            antrianRepository.save(antrian);
            List<Antrian> antrianList = antrianRepository.findByNamaNasabahAndTanggalAntri(request.getNamaNasabah(), sdf.parse(request.getTanggalAntri()));
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

    public Iterable<Antrian> getAllAntrian(){
        return antrianRepository.findAll();
    }

    public Long getNomorAntrian(String id_kategori) {
        return antrianRepository.findAllByNomorAntrian(id_kategori);
    }

    public GetAntrianListOutput getAntrianByKategori (String idKategori){
        List<Antrian> antrianList;
        //Log.d("getData", idKategori);
        List<GetAntrianListOutput.AntrianView> antrianViewList=new ArrayList<>();
        GetAntrianListOutput response = new GetAntrianListOutput();
        GetAntrianListOutput.GetAntrianListResponse getAntrianListResponse=new GetAntrianListOutput.GetAntrianListResponse();
        ErrorSchema errorSchema= new ErrorSchema();
        try{
            if(idKategori==null){
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

}

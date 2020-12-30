package com.antrianservice.service;

import com.antrianservice.entity.antrian.request.PutAntrianRequest;
import com.antrianservice.model.History;
import com.antrianservice.model.Antrian;
import com.antrianservice.entity.antrian.response.GetAntrianListOutput;
import com.antrianservice.entity.antrian.response.PostAntrianOutput;
import com.antrianservice.entity.antrian.response.PostAntrianResponse;
import com.antrianservice.exception.CommonException;
import com.antrianservice.exception.CustomArgsException;
import com.antrianservice.repository.*;
import com.antrianservice.response.ErrorSchema;
import com.antrianservice.util.MessageUtils;
import com.antrianservice.entity.antrian.request.PostAntrianRequest;
import com.antrianservice.response.BaseResponse;
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
    CabangRepository cabangRepository;
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
        //di website pegawai manggil nasabah baru nip keisi/update nip
        try {
            if(request.getIdKategori()==null){
                throw new CustomArgsException("699.not_empty", "idKategori");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Long jmlKategori = kategoriRepository.countKategoriByIdCabang(request.getIdCabang());
            Long nomorAntrianLast=antrianRepository.findAllByNomorAntrian(request.getIdKategori());
            String nomor= String.valueOf(nomorAntrianLast+1);
            int nomorAntri2 = nomor.length();

            if(jmlKategori==0) {
                throw new CustomArgsException("699.not_empty", "");
            }
            String number = String.format("%3s", nomor).replace(" ", "0");
                if (jmlKategori == 1) {
                    number = "A" + number;
                } else if (jmlKategori == 2) {
                    if (request.getIdKategori().equals("1")) {
                        number = "A" + number;
                    } else {
                        number = "B" + number;
                    }
                } else if (jmlKategori == 3) {
                    if (request.getIdKategori().equals("1")) {
                        number = "A" + number;
                    } else if (request.getIdKategori().equals("2")) {
                        number = "B" + number;
                    } else {
                        number = "C" + number;
                    }
                } else if (jmlKategori == 4) {
                    if (request.getIdKategori().equals("1")) {
                        number = "A" + number;
                        ;
                    } else if (request.getIdKategori().equals("2")) {
                        number = "B" + number;
                    } else if (request.getIdKategori().equals("3")) {
                        number = "C" + number;
                    } else {
                        number = "D" + number;
                    }
                } else if (jmlKategori == 5) {
                    if (request.getIdKategori().equals("1")) {
                        number = "A" + number;
                    } else if (request.getIdKategori().equals("2")) {
                        number = "B" + number;
                    } else if (request.getIdKategori().equals("3")) {
                        number = "C" + number;
                    } else if (request.getIdKategori().equals("4")) {
                        number = "D" + number;
                    } else {
                        number = "E" + number;
                    }
                } else if (jmlKategori == 6) {
                    if (request.getIdKategori().equals("1")) {
                        number = "A" + number;
                    } else if (request.getIdKategori().equals("2")) {
                        number = "B" + number;
                    } else if (request.getIdKategori().equals("3")) {
                        number = "C" + number;
                    } else if (request.getIdKategori().equals("4")) {
                        number = "D" + number;
                    } else if (request.getIdKategori().equals("5")) {
                        number = "E" + number;
                    } else {
                        number = "F" + number;
                    }
                }
                antrian.setNomorAntrian(number);

            antrian.setIdKategori(request.getIdKategori());
            antrian.setNamaNasabah(request.getNamaNasabah());
            antrian.setTanggalAntri(sdf.parse(request.getTanggalAntri()));
            antrian.setStatusAntrian(request.getStatusAntrian());
            antrianRepository.save(antrian);
            List<Antrian> antrianList = antrianRepository.findByNamaNasabahAndTanggalAntri(request.getNamaNasabah(), sdf.parse(request.getTanggalAntri()));
            postResponse.setIdAntrian(antrianList.get(0).getIdAntrian());
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

    public GetAntrianListOutput getAntrianListOutput (){
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
            antrianList = antrianRepository.findAllByIdKategori(idKategori);

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

    public BaseResponse updateAntrian(int idAntrian, PutAntrianRequest request){
        BaseResponse response = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        Antrian antrian = antrianRepository.findById(idAntrian).get();
        try {
            if(!antrianRepository.existsById(idAntrian)){
                throw new CustomArgsException("699.not_valid", "idKategori");
            }
            antrian.setNip(request.getNip());
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

}

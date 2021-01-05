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

    public PostAntrianOutput postAntrian(PostAntrianRequest request) throws Exception{
        PostAntrianOutput postOutput = new PostAntrianOutput();
        ErrorSchema errorSchema = new ErrorSchema();
        Antrian antrian = new Antrian();
        Cabang cabang = new Cabang();
        Kategori kategori = new Kategori();
        PostAntrianResponse postResponse = new PostAntrianResponse();
        KategoriService kategoriService = new KategoriService();

        //GetKategoriListOutput kategoriCabang = kategoriService.getKategoriByIdCabang("BCA001");
        List<Kategori> kategoriList = kategoriRepository.findAllByIdCabang(request.getIdCabang());

        //di website pegawai manggil nasabah baru nip keisi/update nip
        try {
            if(!kategoriRepository.existsById(request.getIdKategori())){
                throw new CustomArgsException("699.not_valid", "idKategori");
            }
            if(!kategoriRepository.findIdKategori(request.getIdCabang()).contains(request.getIdKategori())){
                throw new CustomArgsException("699.not_valid", "idKategori");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            antrian.setIdKategori(request.getIdKategori());
            for(Kategori kategori1 : kategoriList) {
                Long nomorAntrianLast = antrianRepository.findAllByNomorAntrian(request.getIdKategori());
                String nomor = String.valueOf(nomorAntrianLast + 1);
                String number = String.format("%3s", nomor).replace(" ", "0");
                String kodeKategori = kategoriRepository.findKodeKategori(request.getIdCabang(),request.getIdKategori());
                String nomorAntrian = kodeKategori + number;
                antrian.setNomorAntrian(nomorAntrian);
            }

            antrian.setNamaNasabah(request.getNamaNasabah());
            antrian.setTanggalAntri(sdf.parse(request.getTanggalAntri()));
            antrian.setStatusAntrian(request.getStatusAntrian());
            antrianRepository.save(antrian);
            List<Antrian> antrianList = antrianRepository.findByNamaNasabahAndTanggalAntriAndIdKategori(request.getNamaNasabah(), sdf.parse(request.getTanggalAntri()), request.getIdKategori());
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

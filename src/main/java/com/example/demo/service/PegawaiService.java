package com.example.demo.service;

import com.example.demo.entity.Pegawai;
import com.example.demo.entity.pegawai.GetUserInfo;
import com.example.demo.entity.pegawai.GetPegawaiListOutput;
import com.example.demo.entity.pegawai.LoginUser;
import com.example.demo.entity.pegawai.LoginUserRequest;
import com.example.demo.exception.CommonException;
import com.example.demo.exception.CustomArgsException;
import com.example.demo.repository.CabangRepository;
import com.example.demo.repository.ErrorMessageRepository;
import com.example.demo.repository.PegawaiRepository;
import com.example.demo.response.BaseResponse;
import com.example.demo.response.ErrorSchema;
import com.example.demo.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

@Service
public class PegawaiService {
    @Autowired
    PegawaiRepository pegawaiRepository;
    @Autowired
    CabangRepository cabangRepository;
    @Autowired
    MessageUtils messageUtils;
    @Autowired
    ErrorMessageRepository errorMessageRepository;

    public BaseResponse postPegawai(String nip, String idCabang, String namaPegawai, String hakAkses, String username, String password) throws Exception{
        BaseResponse response = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        Pegawai pegawai = new Pegawai();
        try {
            if(pegawaiRepository.existsById(nip)){
                throw new CustomArgsException("300", "nip");
            }
            if(nip==null){
                throw new CustomArgsException("699.not_empty", "nip");
            }
            if(idCabang==null){
                throw new CustomArgsException("699.not_empty", "idCabang");
            }
            if(namaPegawai==null){
                throw new CustomArgsException("699.not_empty", "namaPegawai");
            }
            if(hakAkses==null){
                throw new CustomArgsException("699.not_empty", "hakAkses");
            }
            if(username==null){
                throw new CustomArgsException("699.not_empty", "username");
            }
            if(password==null){
                throw new CustomArgsException("699.not_empty", "password");
            }

            //List<Cabang> cabangList=cabangRepository.findAllByIdCabang(idCabang);
            MessageDigest md= MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            String passMd = DatatypeConverter.printHexBinary(digest).toUpperCase();
            pegawai.setNip(nip);
            pegawai.setIdCabang(idCabang);
            pegawai.setNamaPegawai(namaPegawai);
            pegawai.setHakAkses(hakAkses);
            pegawai.setUsername(username);
            pegawai.setPassword(passMd);
            pegawaiRepository.save(pegawai);
            errorSchema.setSuccessResponse();
            response.setErrorSchema(errorSchema);
        } catch (CommonException e) {
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            response.setErrorSchema(errorSchema);
        } catch (Exception e) {
            Object[] args = new Object[]{"Post Pegawai"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(), args));
            response.setErrorSchema(errorSchema);
        }
        return response;
    }

    public Iterable<Pegawai> getAllPegawai(){
        return pegawaiRepository.findAll();
    }

    public GetPegawaiListOutput getPegawaiByIdCabang(String idCabang){
        List<Pegawai> pegawaiList;
        List<GetPegawaiListOutput.PegawaiView> pegawaiViewList=new ArrayList<>();
        GetPegawaiListOutput response = new GetPegawaiListOutput();
        GetPegawaiListOutput.GetPegawaiListResponse getPegawaiListResponse=new GetPegawaiListOutput.GetPegawaiListResponse();
        ErrorSchema errorSchema= new ErrorSchema();
        try{
            if(idCabang==null){
                throw new CustomArgsException("699.not_empty", "idCabang");
            }
            if(!cabangRepository.existsById(idCabang)){
                throw new CustomArgsException("699.not_valid", "idCabang");
            }
            pegawaiList = pegawaiRepository.findAllByIdCabang(idCabang);

            for(Pegawai pegawai : pegawaiList){
                GetPegawaiListOutput.PegawaiView pegawaiView = new GetPegawaiListOutput.PegawaiView();
                String password = pegawai.getPassword();
                MessageDigest md= MessageDigest.getInstance("MD5");
                md.update(password.getBytes());
                byte[] digest = md.digest();
                String passMd = DatatypeConverter.printHexBinary(digest).toUpperCase();
                pegawaiView.setNip(pegawai.getNip());
                pegawaiView.setIdCabang(pegawai.getIdCabang());
                pegawaiView.setNamaPegawai(pegawai.getNamaPegawai());
                pegawaiView.setHakAkses(pegawai.getHakAkses());
                pegawaiView.setUsername(pegawai.getUsername());
                pegawaiView.setPassword(passMd);
                pegawaiViewList.add(pegawaiView);
            }
            getPegawaiListResponse.setPegawaiList(pegawaiViewList);
            response.setOutputSchema(getPegawaiListResponse);
            errorSchema.setSuccessResponse();
            response.setErrorSchema(errorSchema);
        } catch(CommonException e){
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            response.setErrorSchema(errorSchema);
        } catch(Exception e){
            Object[] args = new Object[]{"Get Pegawai by Id Cabang"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(), args));
            response.setErrorSchema(errorSchema);
        }
        return response;
    }

    public GetUserInfo getUserInfo(String nip){
        List<Pegawai> userList;
        List<GetUserInfo.UserView> userViewList=new ArrayList<>();
        GetUserInfo response = new GetUserInfo();
        GetUserInfo.GetUserInfoResponse getUserListResponse=new GetUserInfo.GetUserInfoResponse();
        ErrorSchema errorSchema= new ErrorSchema();
        try{
            if(nip==null){
                throw new CustomArgsException("699.not_empty", "nip");
            }
            if(!pegawaiRepository.existsById(nip)){
                throw new CustomArgsException("699.not_valid", "nip");
            }
            userList = pegawaiRepository.findAllByNip(nip);

            for(Pegawai user : userList){
                GetUserInfo.UserView userView = new GetUserInfo.UserView();
                userView.setNip(user.getNip());
                userView.setHakAkses(user.getHakAkses());
                userView.setIdCabang(user.getIdCabang());
                userView.setNamaPegawai(user.getNamaPegawai());
                userViewList.add(userView);
            }
            getUserListResponse.setUserList(userViewList);
            response.setOutputSchema(getUserListResponse);
            errorSchema.setSuccessResponse();
            response.setErrorSchema(errorSchema);
        } catch(CommonException e){
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            response.setErrorSchema(errorSchema);
        } catch(Exception e){
            Object[] args = new Object[]{"Get User Info"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(), args));
            response.setErrorSchema(errorSchema);
        }
        return response;
    }
    public BaseResponse updatePegawai(String nip, String idCabang, String namaPegawai, String hakAkses, String username, String password){
        BaseResponse response = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        Pegawai pegawai = pegawaiRepository.findById(nip).get();
        try {
            if(nip==null){
                throw new CustomArgsException("699.not_empty", "nip");
            }
            MessageDigest md= MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            String passMd = DatatypeConverter.printHexBinary(digest).toUpperCase();
            pegawai.setNip(nip);
            pegawai.setIdCabang(idCabang);
            pegawai.setNamaPegawai(namaPegawai);
            pegawai.setHakAkses(hakAkses);
            pegawai.setUsername(username);
            pegawai.setPassword(passMd);
            pegawaiRepository.save(pegawai);
            errorSchema.setSuccessResponse();
            response.setErrorSchema(errorSchema);
        } catch (CommonException e) {
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            response.setErrorSchema(errorSchema);
        } catch (Exception e) {
            Object[] args = new Object[]{"Put Pegawai"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(), args));
            response.setErrorSchema(errorSchema);
        }
        return response;
    }

    public BaseResponse loginUser(LoginUserRequest request){
        Pegawai username = pegawaiRepository.findByUsername(request.getUsername());
        BaseResponse response = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        try {
            if (!username.getPassword().equals(request.getPassword())) {
                throw new CustomArgsException("699.not_valid", "password");
            }
            errorSchema.setSuccessResponse();
            response.setErrorSchema(errorSchema);
        }catch (CommonException e) {
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            response.setErrorSchema(errorSchema);
        }
        return response;
    }

    public BaseResponse deletePegawai(String nip){
        Pegawai pegawai = new Pegawai();
        BaseResponse response = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        try{
            if(nip==null){
                throw new CustomArgsException("699.not_empty", "nip");
            }
            pegawaiRepository.deleteById(nip);
            errorSchema.setSuccessResponse();
            response.setErrorSchema(errorSchema);
        } catch (CommonException e){
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            response.setErrorSchema(errorSchema);
        } catch(Exception e){
            Object[] args = new Object[]{"Delete Pegawai"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(),args));
            response.setErrorSchema(errorSchema);
        }
        return response;
    }
}

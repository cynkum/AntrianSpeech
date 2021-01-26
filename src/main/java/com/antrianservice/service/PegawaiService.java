package com.antrianservice.service;

import com.antrianservice.entity.pegawai.request.PostPegawaiRequest;
import com.antrianservice.entity.pegawai.request.PutPegawaiRequest;
import com.antrianservice.entity.pegawai.response.GetUserInfo;
import com.antrianservice.entity.pegawai.request.LoginUserRequest;
import com.antrianservice.entity.pegawai.response.PostLoginOutput;
import com.antrianservice.entity.pegawai.response.PostLoginResponse;
import com.antrianservice.model.Pegawai;
import com.antrianservice.exception.CommonException;
import com.antrianservice.exception.CustomArgsException;
import com.antrianservice.repository.CabangRepository;
import com.antrianservice.repository.ErrorMessageRepository;
import com.antrianservice.repository.PegawaiRepository;
import com.antrianservice.response.ErrorSchema;
import com.antrianservice.util.MessageUtils;
import com.antrianservice.entity.pegawai.response.GetPegawaiListOutput;
import com.antrianservice.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public BaseResponse postPegawai(PostPegawaiRequest request) throws Exception{
        BaseResponse response = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        Pegawai pegawai = new Pegawai();
        try {
            if(pegawaiRepository.existsById(request.getNip())){
                throw new CustomArgsException("300", "nip");
            }
            if(request.getNip()==null){
                throw new CustomArgsException("699.not_empty", "nip");
            }
            if(request.getIdCabang()==null){
                throw new CustomArgsException("699.not_empty", "idCabang");
            }
            if(request.getNamaPegawai()==null){
                throw new CustomArgsException("699.not_empty", "namaPegawai");
            }
            if(request.getHakAkses()==null){
                throw new CustomArgsException("699.not_empty", "hakAkses");
            }
            if(request.getUsername()==null){
                throw new CustomArgsException("699.not_empty", "username");
            }
            if(request.getPassword()==null){
                throw new CustomArgsException("699.not_empty", "password");
            }

            //List<Cabang> cabangList=cabangRepository.findAllByIdCabang(idCabang);
            MessageDigest md= MessageDigest.getInstance("MD5");
            md.update(request.getPassword().getBytes());
            byte[] digest = md.digest();
            String passMd = DatatypeConverter.printHexBinary(digest).toUpperCase();
            pegawai.setNip(request.getNip());
            pegawai.setIdCabang(request.getIdCabang());
            pegawai.setNamaPegawai(request.getNamaPegawai());
            pegawai.setHakAkses(request.getHakAkses());
            pegawai.setUsername(request.getUsername());
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

    public GetPegawaiListOutput getPegawaiListOutput(){
        List<Pegawai> pegawaiList;
        List<GetPegawaiListOutput.PegawaiView> pegawaiViewList=new ArrayList<>();
        GetPegawaiListOutput response = new GetPegawaiListOutput();
        GetPegawaiListOutput.GetPegawaiListResponse getPegawaiListResponse=new GetPegawaiListOutput.GetPegawaiListResponse();
        ErrorSchema errorSchema= new ErrorSchema();
        try{
            pegawaiList = pegawaiRepository.findAll();

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
            Object[] args = new Object[]{"Get Pegawai List"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(), args));
            response.setErrorSchema(errorSchema);
        }
        return response;
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
    public BaseResponse updatePegawai(String nip, PutPegawaiRequest req){
        BaseResponse response = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        Pegawai pegawai = pegawaiRepository.findById(nip).get();
        try {
            if(nip==null){
                throw new CustomArgsException("699.not_empty", "nip");
            }
            MessageDigest md= MessageDigest.getInstance("MD5");
            md.update(req.getPassword().getBytes());
            byte[] digest = md.digest();
            String passMd = DatatypeConverter.printHexBinary(digest).toUpperCase();
            pegawai.setNip(nip);
            pegawai.setIdCabang(req.getIdCabang());
            pegawai.setNamaPegawai(req.getNamaPegawai());
            pegawai.setHakAkses(req.getHakAkses());
            pegawai.setUsername(req.getUsername());
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

    public PostLoginOutput loginUser(LoginUserRequest request){
        String password = pegawaiRepository.existsPegawaiByUsername(request.getUsername());
        Pegawai pegawai = pegawaiRepository.findByUsername(request.getUsername());
        PostLoginOutput output = new PostLoginOutput();
        ErrorSchema errorSchema = new ErrorSchema();
        PostLoginResponse postLoginResponse = new PostLoginResponse();
        String hakAkses = pegawaiRepository.hakAkses(request.getUsername());
        try {
            if (pegawaiRepository.existsPegawaiByUsername(request.getUsername())==null) {
                throw new CustomArgsException("699.not_valid", "username");
            }
            MessageDigest md= MessageDigest.getInstance("MD5");
            md.update(request.getPassword().getBytes());
            byte[] digest = md.digest();
            String passMd = DatatypeConverter.printHexBinary(digest).toUpperCase();

            if (!password.equals(passMd)) {
                throw new CustomArgsException("699.not_valid", "password");
            }
            postLoginResponse.setUsername(request.getUsername());
            postLoginResponse.setHakAkses(hakAkses);
            errorSchema.setSuccessResponse();
            output.setErrorSchema(errorSchema);
            output.setPostLoginResponse(postLoginResponse);

        }catch (CommonException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException((CommonException)e));
            output.setErrorSchema(errorSchema);
        }
        return output;
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

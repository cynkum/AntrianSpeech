package com.antrianservice.controller;

import com.antrianservice.entity.pegawai.request.PostPegawaiRequest;
import com.antrianservice.entity.pegawai.request.PutPegawaiRequest;
import com.antrianservice.entity.pegawai.response.GetUserInfo;
import com.antrianservice.entity.pegawai.response.GetPegawaiListOutput;
import com.antrianservice.entity.pegawai.request.LoginUserRequest;
import com.antrianservice.response.BaseResponse;
import com.antrianservice.service.PegawaiService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@Component
public class PegawaiController {
    @Autowired
    private PegawaiService pegawaiService;

    @PostMapping("/pegawai")
    @ApiOperation(value = "Tambah pegawai cabang")
    public BaseResponse postPegawai(@RequestBody PostPegawaiRequest request) throws Exception {
        return pegawaiService.postPegawai(request);
    }

    @GetMapping("/pegawai/{id-cabang}")
    @ApiOperation(value = "Daftar semua pegawai sesuai cabang")
    public GetPegawaiListOutput getPegawaiByIdCabang(@PathVariable(value = "id-cabang") String idCabang){
        return pegawaiService.getPegawaiByIdCabang(idCabang);
    }

    @GetMapping("{nip}/pegawai")
    @ApiOperation(value = "Detail informasi user")
    public GetUserInfo getUserInfo(@PathVariable (value = "nip") String nip){
        return pegawaiService.getUserInfo(nip);
    }

    @GetMapping("/pegawai")
    @ApiOperation(value = "Daftar semua kategori")
    public GetPegawaiListOutput getPegawaiListOutput(){
        return pegawaiService.getPegawaiListOutput();
    }

    @PutMapping("/pegawai/{nip}")
    public BaseResponse updatePegawai(@PathVariable (value = "nip") String nip, @RequestBody PutPegawaiRequest req){
        return pegawaiService.updatePegawai(nip,req);
    }

    @PostMapping("/pegawai/login")
    public BaseResponse loginUser(@RequestBody LoginUserRequest request){
        return pegawaiService.loginUser(request);
    }

    @DeleteMapping("/pegawai/{nip}")
    public BaseResponse deletePegawai(@PathVariable(value = "nip") String nip){
        return pegawaiService.deletePegawai(nip);
    }

}

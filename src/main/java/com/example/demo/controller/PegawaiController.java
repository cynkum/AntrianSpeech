package com.example.demo.controller;

import com.example.demo.entity.Pegawai;
import com.example.demo.entity.pegawai.GetUserInfo;
import com.example.demo.entity.pegawai.GetPegawaiListOutput;
import com.example.demo.entity.pegawai.LoginUser;
import com.example.demo.entity.pegawai.LoginUserRequest;
import com.example.demo.response.BaseResponse;
import com.example.demo.service.PegawaiService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@Component
public class PegawaiController {
    @Autowired
    private PegawaiService pegawaiService;

    @PostMapping("/pegawai/add")
    @ApiOperation(value = "Tambah pegawai cabang")
    public BaseResponse postPegawai(
            @ApiParam(value = "Silakan isi NIP pegawai", required = true)
            @RequestParam String nip,
            @ApiParam(value = "Silakan isi ID cabang bank", required = true)
            @RequestParam String idCabang,
            @ApiParam(value = "Silakan isi nama pegawai", required = true)
            @RequestParam String namaPegawai,
            @ApiParam(value = "Silakan isi hak akses pegawai", required = true)
            @RequestParam String hakAkses,
            @ApiParam(value = "Silakan isi username pegawai", required = true)
            @RequestParam String username,
            @ApiParam(value = "Silakan isi password pegawai", required = true)
            @RequestParam String password) throws Exception {
        return pegawaiService.postPegawai(nip, idCabang, namaPegawai, hakAkses, username, password);
    }

    @GetMapping("/pegawai/{id-cabang}")
    @ApiOperation(value = "Daftar semua pegawai sesuai cabang")
    public GetPegawaiListOutput getPegawaiByIdCabang(String idCabang){
        return pegawaiService.getPegawaiByIdCabang(idCabang);
    }

    @GetMapping("{nip}/pegawai")
    @ApiOperation(value = "Detail informasi user")
    public GetUserInfo getUserInfo(String nip){
        return pegawaiService.getUserInfo(nip);
    }

    @GetMapping("/pegawai/all")
    @ApiOperation(value = "Daftar semua kategori")
    public Iterable<Pegawai> getAllPegawai(){
        return pegawaiService.getAllPegawai();
    }

    @PutMapping("/pegawai/{nip}")
    public BaseResponse updatePegawai(String nip, String idCabang, String namaPegawai, String hakAkses, String username, String password){
        return pegawaiService.updatePegawai(nip,idCabang,namaPegawai,hakAkses,username,password);
    }

    @PostMapping("/pegawai/login")
    public BaseResponse loginUser(LoginUserRequest request){
        return pegawaiService.loginUser(request);
    }
    @DeleteMapping("/pegawai/{nip}")
    public BaseResponse deletePegawai(String nip){
        return pegawaiService.deletePegawai(nip);
    }

}

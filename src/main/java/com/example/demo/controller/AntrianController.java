package com.example.demo.controller;

import com.example.demo.entity.Antrian;
import com.example.demo.entity.Pegawai;
import com.example.demo.entity.antrian.GetAntrianListOutput;
import com.example.demo.entity.antrian.request.PostAntrianRequest;
import com.example.demo.entity.pegawai.GetPegawaiListOutput;
import com.example.demo.entity.pegawai.GetUserInfo;
import com.example.demo.entity.pegawai.LoginUserRequest;
import com.example.demo.response.BaseResponse;
import com.example.demo.response.PostAntrianOutput;
import com.example.demo.service.AntrianService;
import com.example.demo.service.PegawaiService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/antrian")
@Component
public class AntrianController {
    @Autowired
    private AntrianService antrianService;

    @PostMapping
    @ApiOperation(value = "Tambah antrian cabang")
    public PostAntrianOutput postAntrian(@RequestBody PostAntrianRequest request) throws Exception {
        PostAntrianOutput postAntrianOutput = antrianService.postAntrian(request);
        return postAntrianOutput;
    }

    @GetMapping("/{id-kategori}")
    @ApiOperation(value = "Daftar antrian sesuai kategori antrian")
    public GetAntrianListOutput getAntrianbyKategori(String idKategori){
        return antrianService.getAntrianByKategori(idKategori);
    }

    @PutMapping("/{id-antrian}")
    public BaseResponse updateStatus(int idAntrian, String statusAntrian){
        return antrianService.updateAntrianStatus(idAntrian, statusAntrian);
    }

    @DeleteMapping("/{id-antrian}")
    public BaseResponse deleteAntrian(int idAntrian){
        return antrianService.deleteAntrian(idAntrian);
    }

    @GetMapping("/all")
    @ApiOperation(value = "Daftar semua antrian")
    public Iterable<Antrian> getAllAntrian(){
        return antrianService.getAllAntrian();
    }

    @GetMapping("/{nomor-antri}")
    @ApiOperation(value = "Daftar nomor antrian")
    public Long getNomorAntrian(String id_kategori){
        return antrianService.getNomorAntrian(id_kategori);
    }

    //------------------------------------------------------


}

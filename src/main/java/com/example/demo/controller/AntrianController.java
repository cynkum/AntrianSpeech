package com.example.demo.controller;

import com.example.demo.entity.Antrian;
import com.example.demo.entity.Pegawai;
import com.example.demo.entity.antrian.GetAntrianListOutput;
import com.example.demo.entity.pegawai.GetPegawaiListOutput;
import com.example.demo.entity.pegawai.GetUserInfo;
import com.example.demo.entity.pegawai.LoginUserRequest;
import com.example.demo.response.BaseResponse;
import com.example.demo.service.AntrianService;
import com.example.demo.service.PegawaiService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@Component
public class AntrianController {
    @Autowired
    private AntrianService antrianService;

    @PostMapping("/antrian/add")
    @ApiOperation(value = "Tambah antrian cabang")
    public BaseResponse postAntrian(
            @ApiParam(value = "Silakan isi ID Kategori", required = true)
            @RequestParam String idKategori,
            @ApiParam(value = "Silakan isi nomor antrian", required = true)
            @RequestParam String nomorAntrian,
            @ApiParam(value = "Silakan isi nama nasabah", required = true)
            @RequestParam String namaNasabah,
            @ApiParam(value = "Silakan isi tanggal antri", required = true)
            @RequestParam String tanggalAntri,
            @ApiParam(value = "Silakan isi status antrian", required = true)
            @RequestParam String statusAntrian) throws Exception {
        return antrianService.postAntrian(idKategori, nomorAntrian, namaNasabah, tanggalAntri, statusAntrian);
    }

    @GetMapping("/antrian/{id-cabang}")
    @ApiOperation(value = "Daftar antrian sesuai kategori antrian")
    public GetAntrianListOutput getAntrianbyKategori(String idKategori){
        return antrianService.getAntrianByKategori(idKategori);
    }

    @PutMapping("/antrian/{id-antrian}")
    public BaseResponse updateStatus(int idAntrian, String statusAntrian){
        return antrianService.updateAntrianStatus(idAntrian, statusAntrian);
    }

    @DeleteMapping("/antrian/{id-antrian}")
    public BaseResponse deleteAntrian(int idAntrian){
        return antrianService.deleteAntrian(idAntrian);
    }

    @GetMapping("/antrian/all")
    @ApiOperation(value = "Daftar semua antrian")
    public Iterable<Antrian> getAllAntrian(){
        return antrianService.getAllAntrian();
    }

    //------------------------------------------------------


}

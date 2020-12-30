package com.antrianservice.controller;

import com.antrianservice.entity.kategori.request.PostKategoriRequest;
import com.antrianservice.entity.kategori.request.PutKategoriRequest;
import com.antrianservice.entity.kategori.response.GetKategoriListOutput;
import com.antrianservice.entity.kategori.response.PostKategoriOutput;
import com.antrianservice.model.Kategori;
import com.antrianservice.response.BaseResponse;
import com.antrianservice.service.KategoriService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kategori")
@Component
public class KategoriController {
    @Autowired
    private KategoriService kategoriService;

    @PostMapping()
    @ApiOperation(value = "Tambah kategori antrian")
    public PostKategoriOutput postKategori(@RequestBody PostKategoriRequest request) throws Exception {
        return kategoriService.postKategori(request);
    }

    @GetMapping("/{id-cabang}")
    @ApiOperation(value = "Daftar semua kategori sesuai cabang")
    public GetKategoriListOutput getKategoriByIdCabang(@PathVariable(value = "id-cabang") String idCabang){
        return kategoriService.getKategoriByIdCabang(idCabang);
    }

    @GetMapping()
    @ApiOperation(value = "Daftar semua kategori")
    public GetKategoriListOutput getKategoriListOutput(){
        return kategoriService.getKategoriListOutput();
    }

    @PutMapping("/{id-kategori}")
    public BaseResponse updateKategori(@PathVariable(value = "id-kategori") String idKategori, @RequestBody PutKategoriRequest request){
        return kategoriService.updateKategori(idKategori,request);
    }

    @DeleteMapping("/{id-kategori}")
    public BaseResponse deleteKategori(@PathVariable(value = "id-kategori") String idKategori){
        return kategoriService.deleteKategori(idKategori);
    }

}

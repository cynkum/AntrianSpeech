package com.antrianservice.controller;

import com.antrianservice.entity.kategori.GetKategoriListOutput;
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

    @PostMapping("/add")
    @ApiOperation(value = "Tambah kategori antrian")
    public BaseResponse postCabang(
            @ApiParam(value = "Silakan isi ID kategori antrian", required = true)
            @RequestParam String idKategori,
            @ApiParam(value = "Silakan isi ID cabang bank", required = true)
            @RequestParam String idCabang,
            @ApiParam(value = "Silakan isi jenis antrian", required = true)
            @RequestParam String jenisAntrian,
            @ApiParam(value = "Silakan isi kode kategori", required = true)
            @RequestParam String kodeKategori) throws Exception {
        return kategoriService.postKategori(idKategori, idCabang, jenisAntrian, kodeKategori);
    }

    @GetMapping("{id-cabang}")
    @ApiOperation(value = "Daftar semua kategori sesuai cabang")
    public GetKategoriListOutput getKategoriByIdCabang(String idCabang){
        return kategoriService.getKategoriByIdCabang(idCabang);
    }

    @GetMapping("/all")
    @ApiOperation(value = "Daftar semua kategori")
    public Iterable<Kategori> getAllKategori(){
        return kategoriService.getAllKategori();
    }
    @PutMapping("/{id-kategori}")
    public BaseResponse updateKategori(String idKategori, String idCabang, String jenisAntrian, String kodeKategori){
        return kategoriService.updateKategori(idKategori,idCabang, jenisAntrian, kodeKategori);
    }

    @DeleteMapping("{id-kategori}")
    public BaseResponse deleteKategori(String idKategori){
        return kategoriService.deleteKategori(idKategori);
    }

}

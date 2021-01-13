package com.antrianservice.controller;

import com.antrianservice.entity.antrian.request.PutAntrianRequest;
import com.antrianservice.entity.antrian.response.GetAntrianListOutput;
import com.antrianservice.entity.antrian.response.PostAntrianOutput;
import com.antrianservice.entity.cabang.response.GetCabangListOutput;
import com.antrianservice.entity.kategori.response.GetKategoriListOutput;
import com.antrianservice.model.Antrian;
import com.antrianservice.entity.antrian.request.PostAntrianRequest;
import com.antrianservice.response.BaseResponse;
import com.antrianservice.service.AntrianService;
import com.antrianservice.service.KategoriService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@Component
public class AntrianController {
    @Autowired
    private AntrianService antrianService;
    @Autowired
    private KategoriService kategoriService;

    @PostMapping("/antrian")
    @ApiOperation(value = "Tambah antrian cabang")
    public PostAntrianOutput postAntrian(@RequestBody PostAntrianRequest request) throws Exception {
        PostAntrianOutput postAntrianOutput = antrianService.postAntrian(request);
        return postAntrianOutput;
    }



    @PutMapping("/antrian/{id-antrian}")
    public BaseResponse updateStatus(@PathVariable(value = "id-antrian") int idAntrian, @RequestBody PutAntrianRequest request){
        return antrianService.updateAntrian(idAntrian, request);
    }

    @DeleteMapping("/antrian/{id-antrian}")
    public BaseResponse deleteAntrian(@PathVariable(value = "id-antrian") int idAntrian){
        return antrianService.deleteAntrian(idAntrian);
    }

    @GetMapping("/antrian")
    @ApiOperation(value = "Daftar semua antrian")
    public GetAntrianListOutput getAntrianListOutput(){
        return antrianService.getAntrianListOutput();
    }

    @GetMapping("/{id-kategori}/antrian")
    @ApiOperation(value = "Daftar nomor antrian")
    public Long getNomorAntrian(String id_kategori){
        return antrianService.getNomorAntrian(id_kategori);
    }

    @GetMapping("/antrian/{id-cabang}")
    @ApiOperation("Daftar Kategori Sesuai Cabang")
    public GetKategoriListOutput getKategoriByIdCabang(String idCabang){
        return kategoriService.getKategoriByIdCabang(idCabang);
    }

//    @GetMapping("/antrian/{id-kategori}")
//    @ApiOperation(value = "Daftar semua antrian tanggal hari ini dan kategori yang dipilih")
//    public GetAntrianListOutput getAntrianOrderByTanggalAntri(String idKategori){
//        return antrianService.getAntrianOrderByTanggalAntri(idKategori);
//    }

    @GetMapping("/antrian/{id-kategori}")
    @ApiOperation(value = "Daftar antrian sesuai kategori antrian")
    public GetAntrianListOutput getAntrianbyKategori(String idKategori, boolean isToday){
        return antrianService.getAntrianByKategori(idKategori, isToday);
    }
    //------------------------------------------------------


}

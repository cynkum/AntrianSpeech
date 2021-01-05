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
@RequestMapping("/antrian")
@Component
public class AntrianController {
    @Autowired
    private AntrianService antrianService;
    @Autowired
    private KategoriService kategoriService;

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
    public BaseResponse updateStatus(@PathVariable(value = "id-antrian") int idAntrian, @RequestBody PutAntrianRequest request){
        return antrianService.updateAntrian(idAntrian, request);
    }

    @DeleteMapping("/{id-antrian}")
    public BaseResponse deleteAntrian(@PathVariable(value = "id-antrian") int idAntrian){
        return antrianService.deleteAntrian(idAntrian);
    }

    @GetMapping()
    @ApiOperation(value = "Daftar semua antrian")
    public GetAntrianListOutput getAntrianListOutput(){
        return antrianService.getAntrianListOutput();
    }

    @GetMapping("/{nomor-antri}")
    @ApiOperation(value = "Daftar nomor antrian")
    public Long getNomorAntrian(String id_kategori){
        return antrianService.getNomorAntrian(id_kategori);
    }

    @GetMapping("/{id-cabang}")
    @ApiOperation("Daftar Kategori Sesuai Cabang")
    public GetKategoriListOutput getKategoriByIdCabang(String idCabang){
        return kategoriService.getKategoriByIdCabang(idCabang);
    }
    //------------------------------------------------------


}

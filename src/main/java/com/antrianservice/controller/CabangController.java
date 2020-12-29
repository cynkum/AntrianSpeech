package com.antrianservice.controller;

import com.antrianservice.model.Cabang;
import com.antrianservice.response.BaseResponse;
import com.antrianservice.service.CabangService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cabang")
@Component
public class CabangController {

    @Autowired
    private CabangService cabangService;

    @PostMapping("/add")
    @ApiOperation(value = "Tambah cabang bank")
    public BaseResponse postCabang(
            @ApiParam(value = "Silakan isi ID cabang bank", required = true)
            @RequestParam String idCabang,
            @ApiParam(value = "Silakan isi nama cabang bank", required = true)
            @RequestParam String namaCabang) throws Exception {
        return cabangService.postCabang(idCabang, namaCabang);
    }

    @GetMapping("/all")
    @ApiOperation(value = "Daftar semua cabang bank terdaftar")
    public Iterable<Cabang> getAllCabang(){
        return cabangService.getAllCabang();
    }

    @PutMapping("/{id-cabang}")
    public BaseResponse updateCabang(String idCabang, String namaCabang){
        return cabangService.updateCabang(idCabang, namaCabang);
    }

    @DeleteMapping("{id-cabang}")
    public BaseResponse deleteCabang(String idCabang){
        return cabangService.deleteCabang(idCabang);
    }


}

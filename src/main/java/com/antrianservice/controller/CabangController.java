package com.antrianservice.controller;

import com.antrianservice.entity.cabang.request.PostCabangRequest;
import com.antrianservice.entity.cabang.request.PutCabangRequest;
import com.antrianservice.entity.cabang.response.GetCabangListOutput;
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

    @PostMapping()
    @ApiOperation(value = "Tambah cabang bank")
    public BaseResponse postCabang(@RequestBody PostCabangRequest request) throws Exception {
        return cabangService.postCabang(request);
    }

    @GetMapping()
    @ApiOperation(value = "Daftar semua cabang bank terdaftar")
    public GetCabangListOutput getCabangListOutput(){
        return cabangService.getCabangListOutput();
    }

    @PutMapping("/{id-cabang}")
    public BaseResponse updateCabang(@PathVariable(value="id-cabang") String idCabang, @RequestBody PutCabangRequest request){
        return cabangService.updateCabang(idCabang, request);
    }

    @DeleteMapping("/{id-cabang}")
    public BaseResponse deleteCabang(@PathVariable("id-cabang") String idCabang){
        return cabangService.deleteCabang(idCabang);
    }


}

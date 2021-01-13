package com.antrianservice.controller;

import com.antrianservice.entity.history.request.PostHistoryRequest;
import com.antrianservice.entity.history.response.GetHistoryListOutput;
import com.antrianservice.response.BaseResponse;
import com.antrianservice.service.HistoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @PostMapping()
    @ApiOperation(value = "Tambah history antrian")
    public BaseResponse postHistory(@RequestBody PostHistoryRequest request) throws Exception {
        return historyService.postHistory(request);
    }

    @GetMapping()
    @ApiOperation(value = "Daftar semua history antrian")
    public GetHistoryListOutput getHistoryListOutput() {
        return historyService.getHistoryListOutput();
    }

}


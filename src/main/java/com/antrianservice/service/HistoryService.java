package com.antrianservice.service;

import com.antrianservice.entity.history.request.PostHistoryRequest;
import com.antrianservice.entity.history.response.GetHistoryListOutput;
import com.antrianservice.exception.CommonException;
import com.antrianservice.exception.CustomArgsException;
import com.antrianservice.model.History;
import com.antrianservice.repository.AntrianRepository;
import com.antrianservice.repository.ErrorMessageRepository;
import com.antrianservice.repository.HistoryRepository;
import com.antrianservice.response.BaseResponse;
import com.antrianservice.response.ErrorSchema;
import com.antrianservice.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class HistoryService {
    @Autowired
    HistoryRepository historyRepository;
    @Autowired
    AntrianRepository antrianRepository;
    @Autowired
    MessageUtils messageUtils;
    @Autowired
    ErrorMessageRepository errorMessageRepository;

    public BaseResponse postHistory(PostHistoryRequest request) throws Exception {
        BaseResponse response = new BaseResponse();
        ErrorSchema errorSchema = new ErrorSchema();
        History history = new History();
        try {
//            if(!antrianRepository.existsById(request.getIdAntrian())){
//                throw new CustomArgsException("699.not_valid", "idAntrian");
//            }
            String nip = request.getNip();
            //String idKategori = request.getIdKategori();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar cal = Calendar.getInstance();
            String strDate = sdf.format(cal.getTime());
            String namaNasabah = request.getNamaNasabah();
            String statusAntrian = request.getStatusAntrian();

            history.setNip(nip);
            history.setIdKategori("1");
            history.setTanggal(sdf.parse(strDate));
            history.setNamaNasabah(namaNasabah);
            history.setStatusAntrian(statusAntrian);
            // add data to DB
            historyRepository.save(history);

            // delete data antrian by ID
            antrianRepository.deleteById(request.getIdAntrian());
            errorSchema.setSuccessResponse();
            response.setErrorSchema(errorSchema);

        } catch (CommonException e) {
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            response.setErrorSchema(errorSchema);
        } catch (Exception e) {
            Object[] args = new Object[]{"Post History"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(), args));
            response.setErrorSchema(errorSchema);
        }

        return response;
    }

    public GetHistoryListOutput getHistoryListOutput() {
        List<History> historyList;
        List<GetHistoryListOutput.HistoryView> historyViewList = new ArrayList<>();
        GetHistoryListOutput response = new GetHistoryListOutput();
        GetHistoryListOutput.GetHistoryListResponse getHistoryListResponse = new GetHistoryListOutput.GetHistoryListResponse();
        ErrorSchema errorSchema= new ErrorSchema();
        try {
            historyList = historyRepository.findAll();

            for(History history : historyList) {
                GetHistoryListOutput.HistoryView historyView = new GetHistoryListOutput.HistoryView();
                historyView.setIdHistory(history.getIdHistory());
                historyView.setNip(history.getNip());
                historyView.setIdKategori(history.getIdKategori());
                historyView.setTanggal(history.getTanggal());
                historyView.setNamaNasabah(history.getNamaNasabah());
                historyView.setStatusAntrian(history.getStatusAntrian());
                historyViewList.add(historyView);
            }
            getHistoryListResponse.setHistoryList(historyViewList);
            response.setOutputSchema(getHistoryListResponse);
            errorSchema.setSuccessResponse();
            response.setErrorSchema(errorSchema);
        } catch(CommonException e){
            e.printStackTrace();
            errorSchema.setResponse(messageUtils.setResponseCustomException(e));
            response.setErrorSchema(errorSchema);
        } catch(Exception e){
            Object[] args = new Object[]{"Get History List"};
            errorSchema.setResponse(messageUtils.setResponse(e.getMessage(), args));
            response.setErrorSchema(errorSchema);
        }

        return response;
    }
}

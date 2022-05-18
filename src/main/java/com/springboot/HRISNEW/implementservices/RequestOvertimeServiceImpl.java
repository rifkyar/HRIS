/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TrRequestovertime;
import com.springboot.HRISNEW.interfaceservices.RequestOvertimeServiceInterface;
import com.springboot.HRISNEW.repositories.RequestOvertimeRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USER
 */
@Service
public class RequestOvertimeServiceImpl implements RequestOvertimeServiceInterface {

    @Autowired
    private RequestOvertimeRepo requestOvertimeRepo;

    @Override
    public TrRequestovertime showRequesterOTbyid(String id) {
        return requestOvertimeRepo.findRequesterOTbyid(Integer.parseInt(id));
    }

    @Override
    public TrRequestovertime updateRequestOT(TrRequestovertime reqOT) {
        return requestOvertimeRepo.save(reqOT);
    }

    @Override
    public TrRequestovertime findPMStatus(int idReq) {
        return requestOvertimeRepo.findNullPM(idReq);
    }

    @Override
    public TrRequestovertime findRMStatus(int idReq) {
        return requestOvertimeRepo.findNullRM(idReq);
    }

    @Override
    public TrRequestovertime findMsfcStatus(int idReq) {
        return requestOvertimeRepo.findNullMsfc(idReq);
    }

    @Override
    public int totalWaitingOvertime(int year) {
        return requestOvertimeRepo.totalWaitingOvertime(year);
    }

    @Override
    public int totalDoneOvertime(int year) {
        return requestOvertimeRepo.totalDoneOvertime(year);
    }

    @Override
    public int totalRejectOvertime(int year) {
        return requestOvertimeRepo.totalRejectOvertime(year);
    }

    @Override
    public TrRequestovertime saveRequestOT(TrRequestovertime reqOT) {
        return requestOvertimeRepo.save(reqOT);
    }

    @Override
    public List<Object[]> peopleTrendsOvertime(int month, int month2, int year,int year2) {
        return requestOvertimeRepo.peopleTrendsOvertime(month, month2, year, year2);
    }

//    @Override
    public List<Object[]> soTrendsOvertime(int month, int year) {
        return requestOvertimeRepo.soTrendsOvertime(month, year);
    }

    @Override
    public int doneOvertime(int month, int year) {
        return requestOvertimeRepo.doneOvertime(month, year);
    }

    @Override
    public int rejectOvertime(int month, int year) {
        return requestOvertimeRepo.rejectOvertime(month, year);
    }

    @Override
    public int waitingFromMSFCOvertime(int month, int year) {
        return requestOvertimeRepo.waitingFromMSFCOvertime(month, year);
    }

    @Override
    public int waitingFromRMOvertime(int month, int year) {
        return requestOvertimeRepo.waitingFromRMOvertime(month, year);
    }

    @Override
    public int waitingFromPMOvertime(int month, int year) {
        return requestOvertimeRepo.waitingFromPMOvertime(month, year);
    }

    @Override
    public List<Object[]> OvertimeApproverPending(int year) {
        return requestOvertimeRepo.OvertimeApproverPending(year);
    }

    @Override
    public List<Object[]> OvertimePendingDetail(int id, int year) {
        return requestOvertimeRepo.OvertimePendingDetail(id, year);
    }

}

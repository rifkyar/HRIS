/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.RequestDetail;
import com.springboot.HRISNEW.interfaceservices.RequestDetailServiceInterface;
import com.springboot.HRISNEW.repositories.RequestDetailRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HARRY-PC
 */
@Service
public class RequestDetailServiceImp implements RequestDetailServiceInterface {

    @Autowired
    private RequestDetailRepo requestDetailRepo;

    @Override
    public RequestDetail requestEmpid(String id) {
        return requestDetailRepo.requestEmpid(id);
    }

    @Override
    public List<RequestDetail> usedRequest(int id) {
        return requestDetailRepo.usedRequest(id);
    }

    @Override
    public Integer jumlahId(int id) {
        return requestDetailRepo.jumlahId(id);
    }

    @Override
    public RequestDetail save(RequestDetail rd) {
        return requestDetailRepo.save(rd);
    }

    @Override
    public List<RequestDetail> findListLeaveRequest(int nikDirect, int nikNext) {
        return requestDetailRepo.findListLeaveRequest(nikDirect, nikNext);
    }

    @Override
    public void updateReq(RequestDetail up) {
        requestDetailRepo.save(up);
    }

    @Override
    public List<Object[]> findLeavePeopleTrends(int month, int year) {
        return requestDetailRepo.findLeavePeopleTrends(month, year);
    }

    @Override
    public int findTotalDoneLeave(int year) {
        return requestDetailRepo.findTotalDoneLeave(year);
    }

    @Override
    public int findTotalRejectLeave(int year) {
        return requestDetailRepo.findTotalRejectLeave(year);
    }

    @Override
    public int findTotalWaitingLeave(int year) {
        return requestDetailRepo.findTotalWaitingLeave(year);
    }

    @Override
    public List<Object[]> findSOTrendsLeave(int month, int year) {
        return requestDetailRepo.findSOTrends(month, year);
    }

    @Override
    public int findCountCutiNormal(int month, int year) {
        return requestDetailRepo.findCountCutiNormal(month, year);
    }

    @Override
    public int findCountPerjalananBisnis(int month, int year) {
        return requestDetailRepo.findCountPerjalananBisnis(month, year);
    }

    @Override
    public int findCountIstriMelahirkan(int month, int year) {
        return requestDetailRepo.findCountIstriMelahirkan(month, year);
    }

    @Override
    public int findCountCutiBersalin(int month, int year) {
        return requestDetailRepo.findCountCutiBersalin(month, year);
    }

    @Override
    public int findCountCutiSakit(int month, int year) {
        return requestDetailRepo.findCountCutiSakit(month, year);
    }

    @Override
    public int findCountCutiIjin(int month, int year) {
        return requestDetailRepo.findCountCutiIjin(month, year);
    }

    @Override
    public List<RequestDetail> historyRequestRm(Integer id, Integer ids) {
        return requestDetailRepo.historyRm(id, ids);
    }

    @Override
    public RequestDetail lastRequestEmp(int id) {
        return requestDetailRepo.lastRequestEmp(id);
    }

    //approver pending
    @Override
    public List<Object[]> ApproverPending(int year) {
        return requestDetailRepo.ApproverPending(year);
    }

    @Override
    public List<Object[]> PendingDetail(int id, int year) {
        return requestDetailRepo.PendingDetail(id, year);
    }

}

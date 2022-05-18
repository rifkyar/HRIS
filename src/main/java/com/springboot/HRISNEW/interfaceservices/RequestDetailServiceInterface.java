/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.RequestDetail;
import java.util.List;

/**
 *
 * @author HARRY-PC
 */
public interface RequestDetailServiceInterface {

    RequestDetail requestEmpid(String id);

    RequestDetail lastRequestEmp(int id);

    List<RequestDetail> usedRequest(int id);

    Integer jumlahId(int id);

    RequestDetail save(RequestDetail rd);

    List<RequestDetail> findListLeaveRequest(int nikDirect, int nikNext);

    void updateReq(RequestDetail up);

    List<Object[]> findLeavePeopleTrends(int month, int year);

    List<Object[]> findSOTrendsLeave(int month, int year);

    List<RequestDetail> historyRequestRm(Integer id, Integer ids);

    int findTotalDoneLeave(int year);

    int findTotalRejectLeave(int year);

    int findTotalWaitingLeave(int year);

    int findCountCutiNormal(int month, int year);

    int findCountPerjalananBisnis(int month, int year);

    int findCountIstriMelahirkan(int month, int year);

    int findCountCutiBersalin(int month, int year);

    int findCountCutiSakit(int month, int year);

    int findCountCutiIjin(int month, int year);

    //Approver's Pending Leave
    List<Object[]> ApproverPending(int year);

    //Approver's Pending Detail Leave
    List<Object[]> PendingDetail(int id, int year);
}

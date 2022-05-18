/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TrRequestovertime;
import java.util.List;

/**
 *
 * @author USER
 */
public interface RequestOvertimeServiceInterface {

    TrRequestovertime showRequesterOTbyid(String id);

    TrRequestovertime updateRequestOT(TrRequestovertime reqOT);

    TrRequestovertime saveRequestOT(TrRequestovertime reqOT);

    TrRequestovertime findPMStatus(int idReq);

    TrRequestovertime findRMStatus(int idReq);

    TrRequestovertime findMsfcStatus(int idReq);

    int totalWaitingOvertime(int year);

    int totalDoneOvertime(int year);

    int totalRejectOvertime(int year);

    int doneOvertime(int month, int year);

    int rejectOvertime(int month, int year);

    int waitingFromMSFCOvertime(int month, int year);

    int waitingFromRMOvertime(int month, int year);

    int waitingFromPMOvertime(int month, int year);

    List<Object[]> peopleTrendsOvertime(int month, int month2, int year, int year2);

    List<Object[]> soTrendsOvertime(int month, int year);
    
    
    //Approver's Pending Overtime
    List<Object[]> OvertimeApproverPending(int year);

    //Approver's Pending Overtime
    List<Object[]> OvertimePendingDetail(int id, int year);
}

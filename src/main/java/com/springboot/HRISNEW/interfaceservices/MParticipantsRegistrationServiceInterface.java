/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author User
 */
public interface MParticipantsRegistrationServiceInterface {

    public List<Object[]> getDataParticipants(Integer id);

    public String getTransactionIdForCheck(Integer nik, Integer transaction_id);

    public String getRegisteredUserNikForCheck(Integer nik, Integer transaction_id);

    public List<Object[]> getRegisteredUserForCancelation(Integer transaction_id);

    public List<Object[]> getDataTrainingHistory(Integer id);

    public void approved(Integer id, Integer nik, Timestamp approvedDate);

    public void waitingList(Integer id, Integer nik, Timestamp waitDate);

    public String getCustomerIdbyId(Integer id, Integer nik);

    public String getTrainingTrainerNamebyId(Integer id);

    public String getTrainingSchedulebyId(Integer id);

//    public List<Object[]> getDetailTrainingbyId(Integer id);
    public void rejected(Integer id, Integer nik, Timestamp rejectedDate, String reason);

    public String TrainingTitleForm(Integer id);

    public void accepted(Integer id, Integer nik, Timestamp acceptedDate);

    public void decline(Integer id, Integer nik, Timestamp declinedDate);

    public void checkIn(Integer id, Integer nik, Timestamp checkInDate);

    public void sentFeedback(Integer id, Integer nik, Timestamp sentDate);
    
    public void submitFeedbackUpdateStatus(Integer transaction_id, Integer nik, Timestamp sentDate);

    public void sentDetail(Integer id, Integer nik, Timestamp sentDate, String uuid);
    //CHECKBOX SELECT ALL
    public List<Object[]> getName(Integer transaction_id, Integer[] id);
    
    public String getTrainName(Integer id);
    
    public String getTrainTime(Integer id);
    
    public void bulkReject(Integer[] id, Integer nik, Timestamp sentDate, Integer transaction_id);
    
    public void bulkAccept(Integer[] id, Integer nik, Timestamp sentDate, Integer transaction_id);
    
    public void bulkWaiting(Integer[] id, Integer nik, Timestamp sentDate, Integer transaction_id);
}

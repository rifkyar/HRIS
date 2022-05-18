/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.interfaceservices.MParticipantsRegistrationServiceInterface;
import com.springboot.HRISNEW.repositories.ManageParticipantsRegistrationRepo;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class MParticipantsRegistrationServiceImpl implements MParticipantsRegistrationServiceInterface {

    @Autowired
    ManageParticipantsRegistrationRepo manageParticipantsRegistrationRepo;

    @Override
    public List<Object[]> getDataParticipants(Integer id) {
        return manageParticipantsRegistrationRepo.getDataParticipants(id);
    }

    @Override
    public String getTransactionIdForCheck(Integer nik, Integer transaction_id) {
        return manageParticipantsRegistrationRepo.getTransactionIdForCheck(nik, transaction_id);
    }

    @Override
    public String getRegisteredUserNikForCheck(Integer nik, Integer transaction_id) {
        return manageParticipantsRegistrationRepo.getRegisteredUserNikForCheck(nik, transaction_id);
    }

    @Override
    public List<Object[]> getRegisteredUserForCancelation(Integer transaction_id) {
        return manageParticipantsRegistrationRepo.getRegisteredUserForCancelation(transaction_id);
    }

    @Override
    public List<Object[]> getDataTrainingHistory(Integer id) {
        return manageParticipantsRegistrationRepo.getDataTrainingHistory(id);
    }

    @Override
    public void approved(Integer id, Integer nik, Timestamp approvedDate) {
        manageParticipantsRegistrationRepo.approved(id, nik, approvedDate);
    }

    @Override
    public void waitingList(Integer id, Integer nik, Timestamp waitDate) {
        manageParticipantsRegistrationRepo.waitingList(id, nik, waitDate);
    }

    @Override
    public void rejected(Integer id, Integer nik, Timestamp rejectedDate, String reason) {
        manageParticipantsRegistrationRepo.rejected(id, nik, rejectedDate, reason);
    }

    @Override
    public void accepted(Integer id, Integer nik, Timestamp acceptedDate) {
        manageParticipantsRegistrationRepo.accepted(id, nik, acceptedDate);
    }

    @Override
    public void decline(Integer id, Integer nik, Timestamp declinedDate) {
        manageParticipantsRegistrationRepo.decline(id, nik, declinedDate);
    }

    @Override
    public void checkIn(Integer id, Integer nik, Timestamp checkInDate) {
        manageParticipantsRegistrationRepo.checkIn(id, nik, checkInDate);
    }

    @Override
    public void sentDetail(Integer id, Integer nik, Timestamp sentDate, String uuid) {
        manageParticipantsRegistrationRepo.sentDetail(id, nik, sentDate, uuid);
    }
    
    @Override
    public void submitFeedbackUpdateStatus(Integer transaction_id, Integer nik, Timestamp sentDate) {
        manageParticipantsRegistrationRepo.submitFeedbackUpdateStatus(transaction_id, nik, sentDate);
    }

    @Override
    public void sentFeedback(Integer id, Integer nik, Timestamp sentDate) {
        manageParticipantsRegistrationRepo.sentFeedback(id, nik, sentDate);
    }

    @Override
    public String TrainingTitleForm(Integer id) {
        return manageParticipantsRegistrationRepo.TrainingTitleForm(id);
    }

    @Override
    public String getCustomerIdbyId(Integer id, Integer nik) {
        return manageParticipantsRegistrationRepo.getCustomerIdbyId(id, nik);
    }

    @Override
    public String getTrainingTrainerNamebyId(Integer id) {
        return manageParticipantsRegistrationRepo.getTrainingTrainerNamebyId(id);
    }

    @Override
    public String getTrainingSchedulebyId(Integer id) {
        return manageParticipantsRegistrationRepo.getTrainingSchedulebyId(id);
    }
    //CHECK BOX SELECT ALL
    @Override
    public String getTrainName(Integer id) {
        return manageParticipantsRegistrationRepo.getTrainName(id);
    }

    @Override
    public String getTrainTime(Integer id) {
        return manageParticipantsRegistrationRepo.getTrainTime(id);
    }

    @Override
    public List<Object[]> getName(Integer transaction_id, Integer[] id) {
        return manageParticipantsRegistrationRepo.getName(transaction_id, id);
    }

    @Override
    public void bulkReject(Integer[] id, Integer nik, Timestamp sentDate, Integer transaction_id) {
        manageParticipantsRegistrationRepo.bulkReject(id, nik, sentDate, transaction_id);
    }

    @Override
    public void bulkAccept(Integer[] id, Integer nik, Timestamp sentDate, Integer transaction_id) {
        manageParticipantsRegistrationRepo.bulkAccept(id, nik, sentDate, transaction_id);
    }

    @Override
    public void bulkWaiting(Integer[] id, Integer nik, Timestamp sentDate, Integer transaction_id) {
        manageParticipantsRegistrationRepo.bulkWaiting(id, nik, sentDate, transaction_id);
    }

    }

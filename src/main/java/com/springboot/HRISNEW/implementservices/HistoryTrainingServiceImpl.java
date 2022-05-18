/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.interfaceservices.HistoryTrainingServiceInterface;
import com.springboot.HRISNEW.repositories.HistoryTrainingRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class HistoryTrainingServiceImpl implements HistoryTrainingServiceInterface {

    @Autowired
    HistoryTrainingRepo historyTrainingRepo;

    @Override
    public List<Object[]> getDataTraining() {
        return historyTrainingRepo.getDataTraining();
    }

    @Override
    public List<Object[]> getDetailTraining() {
        return historyTrainingRepo.getDetailTraining();
    }

    @Override
    public List<Object[]> getDataParticipants(Integer id) {
        return historyTrainingRepo.getDataParticipants(id);
    }

    @Override
    public List<Object[]> getUserFeedback(Integer id, Integer nik) {
        return historyTrainingRepo.getUserFeedback(id, nik);
    }

    @Override
    public List<Object[]> getFeedbackSummaryParticipants(Integer id) {
        return historyTrainingRepo.getFeedbackSummaryParticipants(id);
    }

    @Override
    public List<Object[]> getFeedbackSummary(Integer id, Integer nik) {
        return historyTrainingRepo.getFeedbackSummary(id, nik);
    }

    @Override
    public List<Object[]> getAllHistoryAttendance(Integer nik) {
        return historyTrainingRepo.getAllHistoryAttendance(nik);
    }

    @Override
    public List<Object[]> getAllhistoryRequested(Integer nik) {
        return historyTrainingRepo.getAllhistoryRequested(nik);
    }

}

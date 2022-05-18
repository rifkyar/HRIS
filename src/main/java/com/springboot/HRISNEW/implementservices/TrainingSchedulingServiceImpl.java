/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TrainingTransactions;
import com.springboot.HRISNEW.interfaceservices.TrainingSchedulingServiceInterface;
import com.springboot.HRISNEW.repositories.TrainingSchedulingRepo;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class TrainingSchedulingServiceImpl implements TrainingSchedulingServiceInterface {

    @Autowired
    TrainingSchedulingRepo trainingSchedulingRepo;

    @Override
    public List<Object[]> getDataTraining() {
        return trainingSchedulingRepo.getDataTraining();
    }

    @Override
    public List<Object[]> getDetailSchedule() {
        return trainingSchedulingRepo.getDetailSchedule();
    }

    @Override
    public List<Object[]> getScheduleForCheck(String training_Title) {
        return trainingSchedulingRepo.getScheduleForCheck(training_Title);
    }

    @Override
    public TrainingTransactions save(TrainingTransactions t) {
        return trainingSchedulingRepo.save(t);
    }

    @Override
    public void canceltraining(Integer id, Integer nik, Timestamp cancelDate, String remark) {
        trainingSchedulingRepo.canceltraining(id, nik, cancelDate, remark);
    }

    @Override
    public List<Object[]> getDataTrainingWithType() {
        return trainingSchedulingRepo.getDataTrainingWithType();
    }

    @Override
    public List<Object[]> getDataTrainingOnlineClass() {
        return trainingSchedulingRepo.getDataTrainingOnlineClass();
    }

    @Override
    public List<Object[]> getDataTrainingOfflineClass() {
        return trainingSchedulingRepo.getDataTrainingOfflineClass();
    }

    @Override
    public List<Object[]> getDetailDataTrainingOffline() {
        return trainingSchedulingRepo.getDetailDataTrainingOffline();
    }

    @Override
    public List<Object[]> getDetailDataTrainingOnline() {
        return trainingSchedulingRepo.getDetailDataTrainingOnline();
    }

    @Override
    public TrainingTransactions editTrainingTransactionById(int id) {
        return trainingSchedulingRepo.editTrainingTransactionById(id);
    }

    // penambahan 
    @Override
    public List<Object[]> getDetailDataTrainingOnlineAdmin() {
        return trainingSchedulingRepo.getDetailDataTrainingOnlineAdmin();
    }

    @Override
    public List<Object[]> getDataTrainingOnlineClassAdmin() {
        return trainingSchedulingRepo.getDataTrainingOnlineClassAdmin();
    }

    @Override
    public List<Object[]> getDataTrainingOfflineClassAdmin() {
        return trainingSchedulingRepo.getDataTrainingOfflineClassAdmin();
    }

    @Override
    public List<Object[]> getDetailDataTrainingOfflineAdmin() {
        return trainingSchedulingRepo.getDetailDataTrainingOfflineAdmin();
    }

    @Override
    public List<Object[]> getDetailScheduleAdmin() {
        return trainingSchedulingRepo.getDetailScheduleAdmin();
    }

    @Override
    public List<Object[]> getDataTrainingAdmin() {
        return trainingSchedulingRepo.getDataTrainingAdmin();
    }
    
    @Override
    public List<Object[]> getListDataTrainingOnlineAdmin() {
        return trainingSchedulingRepo.getListDataTrainingOnlineAdmin();
    }
    
    @Override
    public List<Object[]> getListDataTrainingOfflineAdmin() {
        return trainingSchedulingRepo.getListDataTrainingOfflineAdmin();
    }

}
